;--------------------------------
;BIOSIM Installer Script using NSIS (SuperPimp)

!define MUI_PRODUCT "BioSim" 
!define MUI_VERSION "1.0"

!include "MUI.nsh"

;--------------------------------
;Configuration
	OutFile "Setup.exe"
	InstallDir "$PROGRAMFILES\BioSim"
	InstallDirRegKey HKEY_LOCAL_MACHINE "SOFTWARE\TRACLabs\BioSim" ""
	DirShow show ; (make this hide to not let the user change it)
;--------------------------------
;Modern UI Configuration
	!define MUI_LICENSEPAGE
	!define MUI_COMPONENTSPAGE
	!define MUI_DIRECTORYPAGE
	!define MUI_ABORTWARNING
	!define MUI_FINISHPAGE
		!define MUI_FINISHPAGE_RUN "$INSTDIR\run-biosim.bat"
	!define MUI_UNINSTALLER
	!define MUI_UNCONFIRMPAGE
	
	;Modern UI System
	!insertmacro MUI_SYSTEM
;--------------------------------
;Languages
	!insertmacro MUI_LANGUAGE "English"
;--------------------------------
;Data
	LicenseData LICENSE.txt
	
;--------------------------------
;Reserve Files

  ;Things that need to be extracted on first (keep these lines before any File command!)
  ;Only useful for BZIP2 compression
  !insertmacro MUI_RESERVEFILE_WELCOMEFINISHPAGE

;--------------------------------
;Install
Section "BioSim Program (must be installed)" ; (default section)
	SetOutPath "$INSTDIR"

	; Try finding Java Runtime
	IntFmt $3 "%u" "1"
	IntFmt $4 "%u" "3"
	ReadRegStr $0 HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" "CurrentVersion"
	ReadRegStr $1 HKLM "SOFTWARE\Javasoft\Java Runtime Environment\$0" "JavaHome"
	StrCmp $1 "" lbl_checkJDK
	StrCpy $5 $0 1 0
	StrCpy $6 $0 1 2
	IntCmp $3 $5 lbl_subscriptCheckOutside lbl_foundJava lbl_checkJDK
	lbl_subscriptCheckOutside:
		IntCmp $4 $6 lbl_foundJava lbl_foundJava lbl_checkJDK
	lbl_checkJDK:
		ReadRegStr $0 HKLM "SOFTWARE\JavaSoft\Java Development Kit" "CurrentVersion"
		ReadRegStr $1 HKLM "SOFTWARE\Javasoft\Java Development Kit\$0" "JavaHome"
		StrCmp $1 "" lbl_noJava
		StrCpy $5 $0 1 0
		StrCpy $6 $0 1 2
		IntCmp $3 $5 lbl_subscriptCheckInside lbl_foundJava lbl_noJava
		lbl_subscriptCheckInside:
			IntCmp $4 $6 lbl_foundJava lbl_foundJava lbl_noJava
	lbl_noJava:
		MessageBox MB_YESNO|MB_ICONQUESTION "Couldn't find Java Runtime Environment > 1.3 installed.$\nThis is required for BioSim to run.$\nIf you chose to continue installation anyway, make sure your JAVA_HOME environment variable is set.$\nDo you want to quit the installation and go to Sun's website to download it?" IDNO lbl_useJavaHome 
	
	lbl_useWebsite:
		ExecShell open "http://java.sun.com/getjava/"
		Sleep 600
		; BringToFront will bring the installer window back to the front
		; if the web browser hides it
		BringToFront
		MessageBox MB_OK "Installation aborted (Java not installed)"
		Quit
	
	lbl_useJavaHome:
		ReadEnvStr $1 "JAVA_HOME"
	
	lbl_foundJava:
		File setENV.bat
		FileOpen $2 "$INSTDIR\setENV.bat" "w"
		FileWrite $2 `set JAVA_HOME="$1"`

	lbl_noWebsite:
		File biosim.jar
		File run-biosim.bat
		File run-biosim-debug.bat
		File biosim.ico
		File LICENSE.txt
		CreateShortCut "$INSTDIR\BioSim.lnk" "$INSTDIR\run-biosim.bat" "" "$INSTDIR\biosim.ico" 0
		WriteRegStr HKEY_LOCAL_MACHINE "SOFTWARE\TRACLabs\BioSim" "" "$INSTDIR"
		WriteRegStr HKEY_LOCAL_MACHINE "Software\Microsoft\Windows\CurrentVersion\Uninstall\BioSim" "DisplayName" "BioSim (remove only)"
		WriteRegStr HKEY_LOCAL_MACHINE "Software\Microsoft\Windows\CurrentVersion\Uninstall\BioSim" "UninstallString" '"$INSTDIR\uninst.exe"'
		; write out uninstaller
		WriteUninstaller "$INSTDIR\uninst.exe"
SectionEnd ; end of BioSim Install

; optional section
Section "Start Menu Shortcuts"
  CreateDirectory "$SMPROGRAMS\BioSim"
  CreateShortCut "$SMPROGRAMS\BioSim\Uninstall.lnk" "$INSTDIR\uninst.exe" "" "$INSTDIR\uninst.exe" 0
  CreateShortCut "$SMPROGRAMS\BioSim\BioSim.lnk" "$INSTDIR\run-biosim.bat" "" "$INSTDIR\biosim.ico" 0
SectionEnd

;--------------------------------
;Uinstall
Section Uninstall
	; add delete commands to delete whatever files/registry keys/etc you installed here.
	Delete "$INSTDIR\uninst.exe"
	Delete "$INSTDIR\biosim.jar"
	Delete "$INSTDIR\run-biosim.bat"
	Delete "$INSTDIR\run-biosim-debug.bat"
	Delete "$INSTDIR\setENV.bat"
	Delete "$INSTDIR\biosim.ico"
	Delete "$INSTDIR\LICENSE.txt"
	Delete "$INSTDIR\BioSim.lnk"
	Delete "$SMPROGRAMS\BioSim\Uninstall.lnk"
	Delete "$SMPROGRAMS\BioSim\BioSim.lnk"
	RMDir "$SMPROGRAMS\BioSim"
	DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\TRACLabs\BioSim"
	DeleteRegKey HKEY_LOCAL_MACHINE "SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\BioSim"
	RMDir "$INSTDIR"
	!insertmacro MUI_UNFINISHHEADER
SectionEnd ; end of uninstall section

