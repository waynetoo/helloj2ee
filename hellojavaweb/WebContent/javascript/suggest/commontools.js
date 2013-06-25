var Suggest_Event = new Object();
Suggest_Event.obvser = function(ele,evt,fun,bool){
  if(ele.addEventListener){
     ele.addEventListener(evt, fun, bool);
  } else{ ele.attachEvent("on"+evt, fun, bool); }
}
Suggest_Event.pointerX = function(event) {
    return event.pageX || (event.clientX +
      (document.documentElement.scrollLeft || document.body.scrollLeft));
  }
Suggest_Event.pointerY = function(event) {
    return event.pageY || (event.clientY +
      (document.documentElement.scrollTop || document.body.scrollTop));
}
Suggest_Event.stop = function(event) {
    if (event.preventDefault) {
      event.preventDefault();
      event.stopPropagation();
    } else {
      event.returnValue = false;
      event.cancelBubble = true;
    }
  }
//如果函数的第一个参数是事件的话，那么返回它
//否则查看其调用者的第一个参数。
function SearchEvent()
{
    func=SearchEvent.caller;
    while(func!=null)
    {
        var arg0=func.arguments[0];
        if(arg0)
        {
            if(arg0.constructor==Event)
                return arg0;
        }
        func=func.caller;
    }
    return null;
}
