package com.timer.coagent;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WindowActivityManager extends javax.swing.JFrame {

    public String getActiveWindowTitle() {
        char[] buffer = new char[1024 * 2];
        WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, buffer, 1024);
        return Native.toString(buffer);
    }

    public String getActiveApplicationName() {
        WinDef.HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        IntByReference pid = new IntByReference();
        User32.INSTANCE.GetWindowThreadProcessId(hwnd, pid);
        WinNT.HANDLE process = Kernel32.INSTANCE
                .OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ, false, pid.getValue());
        if (process == null) {
            return "Unknown";
        }

        char[] exePath = new char[1024];
        Psapi.INSTANCE.GetModuleFileNameExW(process, null, exePath, 1024);
        Kernel32.INSTANCE.CloseHandle(process);
        return Native.toString(exePath);
    }


    public interface User32 extends com.sun.jna.platform.win32.User32 {

        User32 INSTANCE = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        int GetWindowText(WinDef.HWND hWnd, char[] lpString, int nMaxCount);

        WinDef.HWND GetForegroundWindow();

        int GetWindowThreadProcessId(WinDef.HWND hWnd, IntByReference lpdwProcessId);
    }

    public interface Kernel32 extends com.sun.jna.platform.win32.Kernel32 {

        Kernel32 INSTANCE = Native.load("kernel32", Kernel32.class, W32APIOptions.DEFAULT_OPTIONS);

        WinNT.HANDLE OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);

        boolean CloseHandle(WinNT.HANDLE hObject);
    }

    public interface Psapi extends com.sun.jna.platform.win32.Psapi {

        Psapi INSTANCE = Native.load("psapi", Psapi.class, W32APIOptions.DEFAULT_OPTIONS);

        int GetModuleFileNameExW(WinNT.HANDLE hProcess, WinNT.HANDLE hModule, char[] lpBaseName, int nSize);
    }

}
