# How to compile #
## Requirement ##

> - [Netbeans](http://netbeans.org/) IDE or [ant](http://ant.apache.org/) (another neat tool) and [JDK](http://openjdk.java.net/install/) >= 6.0<br />
> - [Izpack](http://izpack.org/) for make Movie Renamer installer

## Compile Movie renamer with ant ##
```
  $ ant
```

It will generate a **`Movie Renamer-$VERSION.jar`** file in dist folder
<br />
## Make Movie Renamer installer (Izpack) ##

  * Required ant and Izpack`

> Install Izpack, add **compile** bin to your shell path
```
  $ sudo ln -s ~/Izpack/bin/compile /usr/bin/compile
```

### Make Unix/Windows installer ###

```
  $ ant mrInstaller
```

It will generate a **`Movie Renamer_Installer-$VERSION.jar`** file in dist folder<br />

### Make Mac OS installer ###

```
  $ ant mrInstallerMac
```

It will generate a **`Movie Renamer_Installer-MacOS-$VERSION.jar`** file in dist folder<br />

### Make all installer ###

```
  $ ant installer
```

It will generate **`both installer`** (Mac Os, Unix/Windows installer) files in dist folder<br />

### Make "real" installer ###

  * equired python

#### Make "real" Mac OS installer ####

> Made Mac OS installer.
> Move to Izpack directory then "**`/utils/wrappers/izpack2app`**"

```
  $ ./izpack2app.py ~/movie-renamer/dist/Movie Renamer_Installer-MacOS-$VERSION.jar Movie Renamer_RealInstaller-MacOS-$VERSION.app
```

#### Make "real" windows installer ####
> Made Unix/Windows installer.
> Move to Izpack directory then "**`/utils/wrappers/izpack2exe`**"

```
  $ ./izpack2exe.py --file=~/movie-renamer/dist/Movie\ Renamer_Installer-MacOS-$VERSION.jar --output=Movie\ Renamer_RealInstaller-Windows-$VERSION.exe --name=Movie\ Renamer
```
