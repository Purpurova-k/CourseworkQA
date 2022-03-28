# Курсовой проект по модулю «Автоматизация тестирования» для профессии «Инженер по тестированию»

Курсовой проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Задача

Автоматизировать сценарии (как позитивные, так и негативные) покупки тура.

[Описание проекта](https://github.com/netology-code/aqa-qamid-diplom)

## Отчетная документация

* [План автоматизации тестирования](https://github.com/Purpurova-k/CourseworkQA/blob/master/Documentation/Plan.md)
* Отчет по итогам тестирования
* Отчет по итогам автоматизации

## Подготовка необходимого окружения

* Интегрированная среда разработки (IDE), например [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows);
* Браузер, например [Google Chrome](https://www.google.com/intl/ru_ru/chrome/);
* [Git](https://git-scm.com/book/ru/v2/%D0%92%D0%B2%D0%B5%D0%B4%D0%B5%D0%BD%D0%B8%D0%B5-%D0%A3%D1%81%D1%82%D0%B0%D0%BD%D0%BE%D0%B2%D0%BA%D0%B0-Git);
* [Docker](https://github.com/netology-code/aqa-homeworks/blob/master/docker/installation.md);
* Десктопное приложение для работы с базами данных, например [DBeaver](https://dbeaver.io/download/).

## Запуск автотестов

* Склонировать репозиторий на локальную машину командой `git clone https://github.com/Purpurova-k/CourseworkQA.git`
* Открыть проект в среде разработки, выбрать систему сборки `Gradle`
* Запустить контейнер с БД MySQL командой в терминале IDE `docker-compose up`
* Дождаться запуска контейнера
* Открыть еще один терминал, ввести команду для запуска приложения `java -jar artifacts/aqa-shop.jar`. Приложение запустится по адресу: http://localhost:8080/
* Запустить тесты командой в терминале `./gradlew clean test`
* Создать отчет по итогам тестирования командой в терминале `./gradlew allureServe`. Автоматически запустится браузер с отчетом
* После прогона автотестов остановить работу контейнера командой в терминале `docker-compose down`

