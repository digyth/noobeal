package com.digyth.noobeal

object Constants {
    val code_js_inject = """
        javascript:
            (function() {
                noobeal.cur=null;
                function onPress(e){
                    noobeal.cur=e.srcElement;
                }
                document.addEventListener('touchstart',onPress);
                document.addEventListener('touchmove',onPress);
                document.addEventListener('touchend',onPress);
            })();
    """.trimIndent()
}