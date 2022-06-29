package com.digyth.noobeal

object Constants {
    val code_js_inject = """
        javascript:
            (function() {
                function readXPath(element) {
                    if (element.id !== "") {
                        return '//*[@id=\"' + element.id + '\"]';
                    }
                    if (element == document.body) {
                        return '/html/' + element.tagName.toLowerCase();
                    }
                    var ix = 1,siblings = element.parentNode.childNodes;
                    for (var i = 0, l = siblings.length; i < l; i++) {
                        var sibling = siblings[i];
                        if (sibling == element) {
                            return arguments.callee(element.parentNode) + '/' + element.tagName.toLowerCase() + '[' + (ix) + ']';
                        } else if (sibling.nodeType == 1 && sibling.tagName == element.tagName) {
                            ix++;
                        }
                    }
                };
                noobeal.cur=null;
                function onPress(e){
                    noobeal.cur=e.srcElement;
                }
                document.addEventListener('touchstart',onPress);
                document.addEventListener('touchmove',onPress);
                document.addEventListener('touchend',onPress);
                noobeal.getUrls=function(type){
                    noobeal.type=type;
                    var urls=[];
                    var a=noobeal.cur.parentNode.getElementsByTagName(type);
                    for(var i=0;i<a.length;i++){
                        switch(type){
                            case 'a':
                                urls.push(a[i].href);
                                break;
                            case 'img':
                                urls.push(a[i].src);
                                break;
                        }
                    }
                    return urls;
                };
                noobeal.expandRange=function(range){
                    noobeal.cur=noobeal.cur.parentNode;
                    return noobeal.getUrls(this.type);
                };
                noobeal.getXPath=function(){
                    return readXPath(noobeal.cur);
                };
            })();
    """.trimIndent()
}