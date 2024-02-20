package com.example.myapplication

class Fon_klavi {
    var imageRes1: String? = null
    var text: String? = null

    constructor() // Пустой конструктор - теперь он не требуется, поскольку все свойства nullable и имеют значения по умолчанию

    constructor(img1: String?, name_fon: String?) {
        this.imageRes1 = img1
        this.text = name_fon
    }
}