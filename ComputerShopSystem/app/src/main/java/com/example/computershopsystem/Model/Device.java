package com.example.computershopsystem.Model;

public abstract class Device {
    CPU cpu;
    Ram ram;
    Rom rom;
    Brand brand;
    Screen screen;

    public Device() {
    }

    public Device(CPU cpu, Ram ram, Rom rom, Brand brand, Screen screen) {
        this.cpu = cpu;
        this.ram = ram;
        this.rom = rom;
        this.brand = brand;
        this.screen = screen;
    }

    public CPU getCpu() {
        return cpu;
    }

    public void setCpu(CPU cpu) {
        this.cpu = cpu;
    }

    public Ram getRam() {
        return ram;
    }

    public void setRam(Ram ram) {
        this.ram = ram;
    }

    public Rom getRom() {
        return rom;
    }

    public void setRom(Rom rom) {
        this.rom = rom;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
