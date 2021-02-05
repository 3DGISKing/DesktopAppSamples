# -*- coding: utf-8 -*-

import sys
import traceback
import glob
import os.path

from PyQt5.QtCore import Qt
from PyQt5.QtWidgets import QMessageBox

def initInterface(pointer):
    from psapp import AppInterface
    from sip import wrapinstance

    global iface
    iface = wrapinstance(pointer, AppInterface)


def psapp_excepthook(type, value, tb):
	print 'exception'
	
	
def setLineEditText():
	iface.setLineEditText("Ok. This comes from python script!")
	
def getLineEditText():
	msg = QMessageBox()
	msg.setWindowTitle("Python Message Box")
	msg.setText(iface.getLineEditText())
	
	msg.exec_()
	
def getCheckBoxState():
	state = iface.getCheckBoxState()
	
	str = ""
	
	if state == Qt.Checked:
		str = "checked!"
	else:
		str = "unchecked!"
		
	msg = QMessageBox()
	msg.setWindowTitle("Python Message Box")
	msg.setText(str)
	
	msg.exec_()
	
def setCheckBoxStateChecked():
	iface.setCheckBoxState(Qt.Checked)
	
def setCheckBoxStateUnchecked():
	iface.setCheckBoxState(Qt.Unchecked)
	
def installErrorHook():
    sys.excepthook = psapp_excepthook

# install error hook() on module load
installErrorHook()

# initialize 'iface' object
iface = None