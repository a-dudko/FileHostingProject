FileHostingProject
==================
Требуется создать web приложение(Serlvet/JSP) для обмена файлами (простая версия https://rapidshare.com/ ). Приложение должно поддерживать следующие возможности:
Обычные пользователи
Загружать файл(до 5 Mb) на сервер – сделать отдельную страничку, на которой имеются поля ввода информации о файле(название, описание, автор, примечания). Предусмотреть проверку корректности вводимых данных, т.е. все необходимые поля заполнены и т. д. Если поля ввода содержат некорректные данные, то подсветить их красным цветом. После успешной загрузки файла показать пользователю сообщение и ссылку на скачивание/удаление файла. Например:
http://localhost:8080/myapp/files/id12345
http://localhost:8080/myapp/files/id12345?op=remove&code=Sj34Z
Параметр code является аналогом пароля подтверждающего правомерность удаления файла.
Скачивать файл по заданной ссылке в которой содержится идентификатор файла, например http://localhost:8080/myapp/files/id12345. Если идентификатор ошибочный - показать соответствующую страницу.
Удалять свои файлы используя ссылку, полученную при загрузке файла на сервер.
Просматривать список файлов, включая информацию о файле и его размер(предусмотреть отдельную странчку). 
Список файлов и их описания вывести в виде таблицы
Модераторы(требуется ввод логина-пароля для входа в систему)
Просматривать список файлов на сервере
Скачивать и загружать файлы как и другие пользователи
Удалять любой файл