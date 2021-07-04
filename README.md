# DCDos


## What is DCDos?
Disk Control And Decision Operating System. 

## General Information
- Project status: **Active**, major side project
- **Not currently recommended for use**: Before I refactored the code of the framework into Spring Boot Starter, there are many functions tailored for myself, and most of these functions **cannot** be used directly in other environments

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
In theory, most functions of Picocli are supported. The implementation of the execution command is in the [`TelegramManager`](https://github.com/YukinaMochizuki/DCDos/blob/master/src/main/java/tw/yukina/dcdos/manager/telegram/TelegramManager.java). You can adjust according to your needs.

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

    @Autowired
    private TelegramUserInfoManager telegramUserInfoManager;

    @Override
    public void run() {
        sendMessageToChatId("Your telegram user id is " + getChatId());
        sendMessageToChatId("Hello!!");
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
    public String getName() {
        return "HelloWorld";
    }

    @Override
    public String getNamespace() {
        return "test";
    }

    @Override
    public String getDepiction() {
        return null;
    }

    @Override
    public void run() {
        stdout("HelloWorld");
        stdout(programController.getInput());
        
        stdout("ReplyMarkup", getOption("ReplyMarkup", 
           ReplyKeyboard.oneLayerStringKeyboard(new String[]{"Key1", "Key2"})));
           
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
### Create a note using dialogue instead of commands
```java
package tw.yukina.dcdos.program.notion;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.notion.entity.thing.Thing;
import tw.yukina.dcdos.notion.entity.thing.ThingUtil;
import tw.yukina.dcdos.notion.request.ThingCreator;
import tw.yukina.dcdos.util.ReplyKeyboard;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ManualCreateThing extends AbstractNotionCreate {
  @Override
  public void run() {
      ThingCreator thingCreator = applicationContext.getBean(ThingCreator.class);

      Thing.ThingBuilder thingBuilder = thingCreator.getThingBuilder();
      thingBuilder.status("New");

      stdout("好的，請說");
      String title = getInput();
      thingBuilder.title(title);

      stdout("請確認所需的 tags，輸入 $done 表示完成",
              getOption("ReplyMarkup", ThingUtil.getTodoThingTagsKeyboard()));
      thingBuilder.tags(getTags());

      stdout("有截止日期嗎？有的話請讓我知道（yyyy/MM/dd）",
              getOption("ReplyMarkup", ReplyKeyboard.oneLayerStringKeyboard(new String[]{"沒有"})));
      String deadLineInput = getInput();
      if(!deadLineInput.equals("沒有"))thingBuilder.deadLineStartDate(deadLineInput);

      String project = getProjectAndPrint();
      String uuid = getStatusUuidAndPrint(title);

      new Thread(() -> {
          thingBuilder.project(getProjectUuid(project));
          updateStdout(title + "\nStatus: 已驗證請求", uuid);
          updateStdout(title + "\nStatus: " + thingCreator.validateAndCreate(), uuid);
      }).start();
  }
}
```
[Full code](https://github.com/YukinaMochizuki/DCDos/blob/69efd01c3d/src/main/java/tw/yukina/dcdos/program/notion/ManualCreateThing.java)</br>
[Demo](http://www.youtube.com/watch?v=wHu39y1T0r4)

### Create multiple notes in a row
```java
package tw.yukina.dcdos.program.notion;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import tw.yukina.dcdos.notion.entity.event.Event;
import tw.yukina.dcdos.notion.entity.event.EventUtil;
import tw.yukina.dcdos.notion.entity.thing.ThingUtil;
import tw.yukina.dcdos.notion.request.EventCreator;
import tw.yukina.dcdos.util.ReplyKeyboard;

import java.util.UUID;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContinuousCreateEvent extends AbstractNotionCreate{
  @Override
  public void run() {
      stdout("好的，請確認事件類型", getOption("ReplyMarkup", EventUtil.getEventTypeKeyboard()));
      String eventType = getInput();

      stdout("請確認事件標籤", getOption("ReplyMarkup", EventUtil.getEventTagsKeyboard()));
      String[] eventTags = getTags();

      stdout("隸屬的專案？", getOption("ReplyMarkup", ThingUtil.getProjectRelationKeyboard()));
      String projectUuid = getProjectUuid(getInput());

      stdout("開始紀錄，輸入 $done 表示完成",
              getOption("ReplyMarkup", ReplyKeyboard.oneLayerStringKeyboard(new String[]{"$done"})));

      while (true){
          String input = getInput();
          if(input.equals("$done"))break;

          EventCreator eventCreator = applicationContext.getBean(EventCreator.class);
          Event.EventBuilder eventBuilder = eventCreator.getEventBuilder();

          eventBuilder.type(eventType);
          eventBuilder.tags(eventTags);
          eventBuilder.project(projectUuid);

          eventBuilder.title(input);

          String uuid = getStatusUuidAndPrint(input);

          new Thread(() -> {
              updateStdout(input + "\nStatus: " + eventCreator.validateAndCreate(), uuid);
          }).start();
      }

      stdout("done");
  }
}
```
[Full code](https://github.com/YukinaMochizuki/DCDos/blob/69efd01c3d/src/main/java/tw/yukina/dcdos/program/notion/ContinuousCreateEvent.java)</br>
[Demo](http://www.youtube.com/watch?v=1J8Ds4VYb4w)

## Roadmap


## Some implementation details


