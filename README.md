# ariia  (0.2.9)

Ariia is a command line download manager and HTML UI interface [Angular 11].

![screenshot-01](img/angular-spa-01.png)

## Dependency

Lib Name|Version|Info
--- | --- | ---
OkHttp|`3.14.9`|for jdk8 only|
Gson|`2.8.6`| |
Network Speed|`0.2.8`| |
Network Connectivity|`0.2.8`| |
RESTful API|`0.2.8`| |
Lawnha|`0.2.1`| |
JANSI|`1.8`|for Windows VM|
 
## Modules
 
- Network
- Logger
- Models
- Utils
- Network Monitor
- Core API
- Verify Data
- CLI API
- Ariia JDK 8
- Ariia JDK 11
- Restful API
- Web Server
- WEB UI (Angular SPA)

![screenshot-01](img/dependency-hierarch.png)
___

### Options :
 - support HTTP and HTTPS.
 - support Header and Cookies
 - cross-Platform: support Linux, Unix, Windows and Mac OS.
 - parallel download, using segment.
 - saved setting every 1 second.
 - support Google Chrome with extension.
 - support using Proxy [HTTP, HTTPS, SOCKS], COMMING SOON JSCH(SSH)
 - support download from maven repository
 - supported arguments
 - save time while downloading, by spiriting download process and writing data to hard disk.
 - using cache memory, to reduce hate resulted by continuous writhing to (mechanical/old/magnet hard disk),
 		not test on ssd Hard Disk
 - solve heat problem, by reduce write time to hard disk - flush on fixed rate of time every 5s.
  
___ 
 
 ```
java - jar ariia.jar [-u] URL
	-u	--url			[-u] add new link/url to download manager
	-i	--input-file		downoload from text file - list of urls
	-m	--metalink		downoload from  metalink text/xml file - list of urls on deffrient servers for the same daownloadable file
	-r	--http-referer		set referer header for that link
	-ua	--user-agent		set user-agent header while download
	-H	--header		set one/multiable different header(s) for that link
			add cookie(s) while download
	-cf	--cookie-file		add cookie(s) from standered cookie file
	-o	--file-name		save download link to file on hard-disk
	-sp	--save-path		set directory of download process
	-t	--tries			number of tries when failler, then giveup (0 for keep-try )
	-c	--max-connection	max connection for current session for each link
	-n	--num-download		number of download links in queue, if more links, will be in watting list
	-p	--proxy			set proxy to http://host:port[8080]/, support protocols http, https ans socks4/5
	-http	--http-proxy		use http proxy [host:port] format
	-https	--https-proxy		use https proxy [host:port] format
	-socks	--socks-proxy		use socks proxy [host:port] format
	-socks4	--socks4-proxy		use socks4 proxy [host:port] format
	-socks5	--socks5-proxy		use socks5 proxy [host:port] format
	-s	--ssh			use ssh connection as proxy - [remotehost:port], not supported yet
	-su	--ssh-user		set ssh user name - remote login user name
	-sp	--ssh-pass		set remote login password, if non will be asked from terminal
	-ch	--check-file		check donload file if is complete, and try to complete it
	-cs	--chunk-size		length of shunk/segment to check
	-dp	--download-pieces	index of pieces which need download. it could be in formate of string as "2 52 22 783 " or a file holding the indexs separited by '\n'
	-st	--stream		stream URL One download connection
	-ds	--daemon-service	start ariia as daemon service
	-port	--server-port		run web application on port (default port 8080)
	-host	--server-host		run web application for local interface (defult is any all)
	-rl	--resource-location	run web application with resource location directory path
	-h	--help			print this message
	-d	--debug-level		display logging, Levels: [off, log, error, warn, info, assertion, debug, trace, all]
	-v	--version		display the version of ariia

 ```

### TO:DO:LIST

 - add SSH implementation

### Overview
![screenshot-01](img/angular-spa-02.png)
___
![screenshot-01](img/angular-spa-03.png)
___
![screenshot-01](img/download-ubuntu-mini.gif)
___
![screenshot-01](img/mini-table-01.png)
___
![screenshot-02](img/mini-table-02.png)

### Text Link Format: 
![Format1](img/text-format01.png)
___
![Format2](img/text-format02.png)
