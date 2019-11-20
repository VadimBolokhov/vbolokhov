[![Build Status](https://travis-ci.org/VadimBolokhov/vbolokhov.svg?branch=master)](https://travis-ci.org/VadimBolokhov/vbolokhov)
[![codecov](https://codecov.io/gh/VadimBolokhov/vbolokhov/branch/master/graph/badge.svg)](https://codecov.io/gh/VadimBolokhov/vbolokhov)

# Репозиторий Вадима Болохова

Я прохожу обучение у преподавателя [Петра Арсентьева](https://ru.linkedin.com/in/petr-arsentev-3b196927) по курсу [Фуллстек Java + JS](https://job4j.ru/courses/java_with_zero_to_job.html)  

В настоящий момент осваиваю тему: Spring Framework

Все задания курса объединены в один maven-проект и разделены на модули по темам следующим образом:

+ *chapter_001* - вводная часть, базовый синтаксис, работа с массивами, maven, JUnit
+ *chapter_002* - ООП, исключения
+ *chapter_003* - коллекции, обобщения, компараторы
+ *chapter_004* - коллекции: структуры данных, итераторы
+ *chapter_005* - многопоточность
+ *chapter_006* - SQL, JDBC
+ *chapter_007* - сервлеты
+ *chapter_008* - ввод-вывод
+ *chapter_009* - Hibernate

### Наиболее интересные проекты, над которыми я работал в процессе обучения:

- [Парсер вакансий sql.ru](https://github.com/VadimBolokhov/job4j/tree/master/chapter_006/src/main/java/ru/job4j/vacancy)
Приложение извлекает информацию о вакансиях, содержащих 'java' (не 'JavaScript') в заголовке с форума сайта sql.ru и помещает в базу данных.

  Использование:\
  `SqlRuParser <настройки>`\
  где <настройки> - это файл в формате .properties, который должен содержать свойства:
  - настройки базы данных:
    - jdbc.username
    - jdbc.password
    - jdbc.driver
    - jdbc.url
  - расписание обновлений базы ([CronTrigger](http://www.quartz-scheduler.org/documentation/quartz-2.3.0/tutorials/crontrigger.html))
    - cron.time


- [Zip-архиватор](https://github.com/VadimBolokhov/job4j/tree/master/chapter_008/src/main/java/ru/job4j/files)
Использование: `pack -d <source directory> -o <output> [-e <extensions to exclude>]`\
Ключ -e задаёт список расширений файлов, которые не нужно включать в архив

- [Cars Store](https://github.com/VadimBolokhov/job4j/tree/master/chapter_009/src/main/java/ru/job4j/cars) - Web-приложение, реализующее площадку для продажи машин.

![index.html](/images/cars/index.jpg)

Приложение позволяет размещать объявления о продаже автомобилей с возможностью загружать и удалять фотографии

![Adding a car](/images/cars/addcar.jpg)

![Car details](/images/cars/details.jpg)

Реализована возможность авторизации пользователей

![Log in](/images/cars/login.jpg)