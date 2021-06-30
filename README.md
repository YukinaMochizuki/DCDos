# DCDos


## What is DCDos?
Disk Control And Decision Operating System. 

## General Information
- Project status: **Active**, major side project

## Table of Contents
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Build](#build)
- [Usage](#usage)
- [Examples](#examples)
- [Roadmap](#roadmap)


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


## Build
### To Jar
```Shell
git clone https://github.com/YukinaMochizuki/DCDos.git
./gradlew assemble
```

### To Dosker image
After `gradlew assemble`
```Shell
docker build .
```

## Usage
### Command


### Program



## Examples
### Create a note using dialogue instead of commands
```java
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


## Some development details



