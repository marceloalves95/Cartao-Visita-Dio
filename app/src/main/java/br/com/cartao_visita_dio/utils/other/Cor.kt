package br.com.cartao_visita_dio.utils.other

/**
 * @author RubioAlves
 * Created 27/06/2021 at 20:14
 */
enum class Cor(val nomeCor: String, val cor: String, val corPrimaryDark:String, val textColor:String) {
    AMBER("Amber","#FFC107", "#FFA000","#212121"),
    BLUE("Blue","#2196F3","#1976D2","#FFFFFF"),
    BLUEGREY("Blue Grey","#607D8B","#455A64","#FFFFFF"),
    BROWN("Brown","#795548","#5D4037","#FFFFFF"),
    CYAN("Cyan","#00BCD4", "","#FFFFFF"),
    DEEPPURPLE("Deep Purple","#673AB7","","#FFFFFF"),
    DEEPORANGE("Deep Orange","#FF5722","#E64A19","#FFFFFF"),
    GREEN("Green","#4CAF50","#388E3C","#FFFFFF"),
    GREY("Grey","#9E9E9E","#616161","#212121"),
    INDIGO("Indigo", "#3F51B5","#303F9F","#FFFFFF"),
    LIGHTBLUE("Light Blue","#03A9F4","#0288D1","#FFFFFF"),
    LIGHTGREEN("Light Green","#8BC34A","#689F38","#212121"),
    LIME("Lime","#CDDC39","#AFB42B","#212121"),
    PINK("Pink","#E91E63","#C2185B","#FFFFFF"),
    PURPLE("Purple","#9C27B0","#7B1FA2","#FFFFFF"),
    RED("Red", "#F44336","#D32F2F","#FFFFFF"),
    TEAL("Teal","#009688","#00796B","#FFFFFF"),
    YELLOW("Yellow","#FFEB3B","#FBC02D","#212121")
}