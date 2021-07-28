# DCDos
Don't want to read so many words? Jump to [Demo](#examples).

This is personal assistant with both dialogue and command interface. 

Strictly speaking, DCDos is a combination of framework and application. In the near future, I will release part of the framework separately as spring-boot-starter to make its functions easier to use.

## What is DCDos?
> Disk Control And Decision Operating System.

When working and living, I very much hope that I can have a personal assistant to help me organize notes and to-dos. Although [Notion](https://www.notion.so) is powerful, it is still difficult to operate this complex and huge machine efficiently during meetings or busy times.

DCDos will act as a controller between the brain and the notes, just like the hard disk controller processes data from the disk to the memory.

## General Information
- Project status: **Active**, major side project
- **Not currently recommended for use**: Before I refactored the code of the framework into spring-boot-starter, there are many functions tailored for myself, and most of these functions **cannot** be used directly in other environments

## Table of Contents
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Build](#build)
- [Usage](#usage)
- [Examples](#examples)
- [Roadmap](#roadmap)
- [Some implementation details](#some-implementation-details)


## Technologies Used
### Development environment
- AdoptOpenJDK 11 with HotSpot JVM
- Software Development Kit Manager: SDKMAN!
- Build System: Gradle 6.8.3
- Database: MariaDB
- CI/CD:
   - [gitlab-ci.yml](https://github.com/YukinaMochizuki/DCDos/blob/master/.gitlab-ci.yml) - This GitHub repository is actually a mirror from my GitLab
   - [Dockerfile](https://github.com/YukinaMochizuki/DCDos/blob/master/Dockerfile) - The gitlab-ci will package the application into an image

### Dependencies
- Spring Boot 2.4.5
  - Spring Boot Starter Web
  - Spring Boot Starter Security
  - Spring Boot Starter Data JPA
- [JSch](http://www.jcraft.com/jsch/) - A pure Java implementation of SSH2. Used to control the unofficial API server
- [Apache Commons Validator](https://commons.apache.org/proper/commons-validator/) - A powerful and customized data verification framework. Used to verify whether the request sent to the API server conforms to the format
- [Picocli](https://picocli.info) - Used to build rich command line applications then connect to communication software
- [TelegramBots](https://github.com/rubenlagus/TelegramBots) - Java library to create bots using Telegram Bots API
- [Project Lombok](https://projectlombok.org) - Getter, Setter, ToString, AllArgsConstructor and more..

## Features
### Portability
[Program](#program) doesn’t know where the message it sends will go, also doesn’t know where the message it receives comes from. All operations are dependent on the abstract class [`AbstractSession`](https://github.com/YukinaMochizuki/DCDos/blob/master/src/main/java/tw/yukina/dcdos/session/AbstractSession.java).

So as long as any class extends [`AbstractSession`](https://github.com/YukinaMochizuki/DCDos/blob/master/src/main/java/tw/yukina/dcdos/session/AbstractSession.java) and implements the above method, then it can assume the responsibility of message transmission like [`TelegramSession`](https://github.com/YukinaMochizuki/DCDos/blob/master/src/main/java/tw/yukina/dcdos/session/telegram/TelegramSession.java).

## Setup
### Build
#### To Jar
```Shell
git clone https://github.com/YukinaMochizuki/DCDos.git
./gradlew assemble
```

#### To Docker image
After `gradlew assemble`
```Shell
docker build --tag dcdos .
```

### Configuration
#### application.properties
Put it in `src/main/resources`, or in `/app/config` in the container.

```properties
dcdos.debug=false
telegram.username=Your_Bot_Username
telegram.token=12345:ABCDEF
telegram.permission.master=123456789
notion.api.baseurl=http://your-dcdos-notion-api-url
```

### Deploy
You may need to modify these commands for your own environment.

#### In my case
```Shell
docker pull registry.lan.yukina.tw/shuvi/dcdos:latest
docker start dcdos-notion-api
docker run -d \
   -v /path/DCDos/config:/app/config \
   -v /path/DCDos/ssh:/app/ssh \
   --link dcdos-notion-api \
   --name dcdos \
   registry.lan.yukina.tw/shuvi/dcdos:latest
```

## Usage
### Command
In theory, most functions of [Picocli](https://picocli.info) are supported. The implementation of the execution command is in the [`TelegramManager`](https://github.com/YukinaMochizuki/DCDos/blob/master/src/main/java/tw/yukina/dcdos/manager/telegram/TelegramManager.java). You can adjust according to your needs.

```java
@Component
public class TelegramManager {
   public void messageInput(Update update){
   
   ...other code
   
   // find all of the command, assistantCommands is initialized at constructor injection
      Optional<AbstractAssistantCommand> assistantCommandOptional =
         assistantCommands.stream()
         .filter(command -> parameter.get(0).equals(command.getCommandName())).findAny();

   
   ...other code
   
   // execute command
      if(assistantCommandOptional.isPresent()) {
         new CommandLine(assistantCommand).setOut(writer).setErr(writer)
            .setCaseInsensitiveEnumValuesAllowed(true)
            .setUsageHelpWidth(100).execute(args);
      }
   }
}

```
[Full code](https://github.com/YukinaMochizuki/DCDos/blob/master/src/main/java/tw/yukina/dcdos/manager/telegram/TelegramManager.java)


So you can simply extend [`AbstractAssistantCommand`](https://github.com/YukinaMochizuki/DCDos/blob/master/src/main/java/tw/yukina/dcdos/command/AbstractAssistantCommand.java) and implements [`Runnable`](https://docs.oracle.com/javase/8/docs/api/java/lang/Runnable.html) to create a command.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import picocli.CommandLine.Command;
import tw.yukina.dcdos.command.AbstractAssistantCommand;
import tw.yukina.dcdos.constants.Role;
import tw.yukina.dcdos.manager.telegram.TelegramUserInfoManager;

@Component
@Command(name = "start", description = "Say Hello")
public class Start extends AbstractAssistantCommand implements Runnable {

    @Override
    public void run() {
        sendMessageToChatId("Your telegram user id is " + getChatId());
        sendMessageToChatId("If you want to get some help, please send /help for me");
    }

    @Override
    public String getCommandName() {
        return "start";
    }

    @Override
    public Role[] getPermissions() {
        return new Role[]{Role.GUEST};
    }
}
```

<img src="https://user-images.githubusercontent.com/26710554/124572937-a6759700-de7b-11eb-8935-9686ebcbeb8b.png" alt="drawing" width="400"/>


### Program
Just like creating a command, you can create a program by extends [`AbstractProgramCode`](https://github.com/YukinaMochizuki/DCDos/blob/master/src/main/java/tw/yukina/dcdos/program/AbstractProgramCode.java).

```java
package tw.yukina.dcdos.program.test;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.program.AbstractProgramCode;
import tw.yukina.dcdos.util.ReplyKeyboard;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestProgram extends AbstractProgramCode {

    @Override
    public String[] getKeyword() {
        return new String[]{"測試軟體1"};
    }

    @Override
    public void run() {
        stdout("HelloWorld");
        stdout(getInput());
        stdout("HelloWorld after HelloWorld2");
    }
}
```
Notice `getInput()` is synchronous, so blocking occurs in `stdout(programController.getInput());`.

<img src="https://user-images.githubusercontent.com/26710554/124372521-05d97880-dcbd-11eb-9141-0dd32c5b076f.png" alt="drawing" width="300"/>

#### Edit sent message
Only need to provide the id when sending the message, and then you can use that id to edit the message.

```java
 @Override
 public void run() {
     stdout("testMessage", "uuid1");
     stdout("testMessage2", "uuid2");

     try {
         Thread.sleep(1000);
         stdout("after");
         updateStdout("after edit", "uuid1");
         updateStdout("after edit2", "uuid2");
     } catch (InterruptedException e) {
         e.printStackTrace();
     }
 }
```
[Full code](https://github.com/YukinaMochizuki/DCDos/blob/master/src/main/java/tw/yukina/dcdos/program/test/TestEditMessage.java)

<img src="https://i.imgur.com/iRsE48f.gif" alt="drawing" width="300"/>

## Examples
### Create a note using dialogue
<!-- [Demo](http://www.youtube.com/watch?v=wHu39y1T0r4) -->

[Create a note using dialogue instead of commands](https://www.youtube.com/watch?v=wHu39y1T0r4 "Create a note using dialogue instead of commands")

[Full code](https://github.com/YukinaMochizuki/DCDos/blob/69efd01c3d/src/main/java/tw/yukina/dcdos/program/notion/ManualCreateThing.java)

### Asynchronous create multiple notes
<!-- [Demo](http://www.youtube.com/watch?v=1J8Ds4VYb4w) -->

[![Create multiple notes in a row](https://user-images.githubusercontent.com/26710554/124569507-96a88380-de78-11eb-9022-a82ea138e12d.png)](https://www.youtube.com/watch?v=1J8Ds4VYb4w "Create multiple notes in a row")

[Full code](https://github.com/YukinaMochizuki/DCDos/blob/69efd01c3d/src/main/java/tw/yukina/dcdos/program/notion/ContinuousCreateEvent.java)

## Roadmap


## Some implementation details



