/*
 * 本suggest代码参考了51map的suggest代码，css菜单的代码。
 * 舍弃了offsetParent获取偏移量的方法。
 * setValueCallback:设置完input值以后会触发这个回调函数
 */

function inputSuggest(options,setValueCallback){
	this.leftOffset = 0;
	var thisRef = this;
	this.inputTxt = options.inputTxt;
	this.setValueCallback = setValueCallback==null?null:setValueCallback;
	this.evt = null;
	this.currentDiv = null;
	this.isEnter = false;
//	标志位,当前菜单是否生成
	this.isSuggest = false;
//	设置自动完成为fasle
	document.getElementById(this.inputTxt).setAttribute("autocomplete","off");
	Suggest_Event.obvser(document.getElementById(this.inputTxt),"keydown",function(){return thisRef.keyDownFn.call(thisRef)},false);
	Suggest_Event.obvser(document.getElementById(this.inputTxt),"blur",function(){return thisRef.hiddenDelegate.call(thisRef)},false);
	Suggest_Event.obvser(document.body,"click",function(){return thisRef.hiddenSuggest.call(thisRef)},false);
	function onKeyupFun(){return  thisRef.keyupFunDelegate.call(thisRef,document.getElementById(thisRef.inputTxt))};
	Suggest_Event.obvser(document.getElementById(this.inputTxt),"keyup",onKeyupFun,false);
	//不知道这个是否会带来其他问题
	function onDblCLickFun(){thisRef.dblclick=true;return  thisRef.keyupFunDelegate.call(thisRef,document.getElementById(thisRef.inputTxt))};
	Suggest_Event.obvser(document.getElementById(this.inputTxt),"dblclick",onDblCLickFun,false);
	Suggest_Event.obvser(document.getElementById(this.inputTxt),"click",function(){return  thisRef.stopEvent.call(thisRef)},false);
}
//inputSuggest.isSuggest = false;
//保存当前被选中的节点
//inputSuggest.currentDiv = null;
//菜单显示时所要过滤的键
inputSuggest.filterKeyCodesHidden = [13];
//没过滤tab键的原因是tab键移动到下一个input的时候可以激发keyup事件
//菜单隐藏时所要过滤的键
//inputSuggest.filterKeyCodes = [38,40,37,39,13,16,37,39];
inputSuggest.filterKeyCodes = [38,40,37,39,16,37,39,27,33,45,34,16,35,144,17,36,18,20,112,113,114,115,116,117,118,119,120,121,122,123];
inputSuggest.filterInputValue = [];//原来有个元素是""，现在为了支持双击事件去掉了

inputSuggest.prototype.stopEvent = function(){
	var evt;
	if(window.event){
        evt = window.event;
    }else{
        evt = this.stopEvent.caller.arguments[0];
    }
	Suggest_Event.stop(evt);
}
inputSuggest.prototype.suggestRequest = function(keyWord,callBackHandle){
	var obj = this;
	this.keyupCallback(true).call(this);

}
//点击的时候blur事件会先发生,这时候菜单会被隐藏掉,导致无法发生click事件,所有这里要加一个延时隐藏
inputSuggest.prototype.hiddenDelegate = function(){
	var obj = this;
	window.setTimeout(function(){return obj.hiddenSuggest.call(obj);},200);
}
inputSuggest.prototype.hiddenSuggest = function(){
	if(document.getElementById("container_"+this.inputTxt)){
        document.getElementById("container_"+this.inputTxt).style.display = "none";
        this.resetParentNode();
	//	设置状态
		this.isSuggest = false;
		this.currentDiv = null;
    }
}
inputSuggest.prototype.showSuggest = function(){
	if(document.getElementById("container_"+this.inputTxt)){
        document.getElementById("container_"+this.inputTxt).style.display = "inline";
    }
}
inputSuggest.prototype.isShow = function(){
	if(document.getElementById("container_"+this.inputTxt)){
		if(document.getElementById("container_"+this.inputTxt).style.display == "inline"){
			return true;
	    }else {
			return false;
		}
	}
	return false;
}
//按键不触发提交
inputSuggest.prototype.filterKeyCodesFun = function(){
//	如果隐藏
//	单独的判断enter键
	if(evt.keyCode==13&&this.isEnter){
		this.isEnter = false;
		return true;
	}

	if(!this.isShow()){
//		if hidden ,press up or down can show
//		上下会触发提交
//        if(evt.keyCode==13||evt.keyCode==38||evt.keyCode==40){
        if(evt.keyCode==38||evt.keyCode==40){
			return false;
		}else{
//			隐藏时哪些键不会触发提交
			for(var j=0;j<inputSuggest.filterKeyCodes.length;j++){
				if(inputSuggest.filterKeyCodes[j]==evt.keyCode){
					return true;
				}
			}
			
		}
	}else{
//		显示时哪些键不触发提交
		for(var i=0;i<inputSuggest.filterKeyCodes.length;i++){
	        if(evt.keyCode==inputSuggest.filterKeyCodes[i]){return true;}
	    }
	}
	return false;
}
inputSuggest.prototype.keyDownFn = function(){
    this.evt = this.keyDownFn.caller.arguments[0] || window.event;
    if(!this.isSuggest) return;
	var inputSuggText = document.getElementById(this.inputTxt);
	if(this.evt.keyCode==38){
		  if(this.currentDiv==null){
		        this.currentDiv = document.getElementById("container_"+this.inputTxt).lastChild;
				this.currentDiv.className = "tpsEleSeled";
				this.currentDiv.setAttribute("class","tpsEleSeled");
				inputSuggText.value = this.currentDiv.dataObj.name;
				if(this.inputTextChange)this.inputTextChange();
          }else{
        		if(this.currentDiv.previousSibling!=null){
					this.currentDiv.className = "tpsEle";
					this.currentDiv.setAttribute("class","tpsEle");
        			this.currentDiv = this.currentDiv.previousSibling;
					this.currentDiv.className = "tpsEleSeled";
					this.currentDiv.setAttribute("class","tpsEleSeled");
					inputSuggText.value = this.currentDiv.dataObj.name;
					if(this.inputTextChange)this.inputTextChange();
    			}else{
					this.currentDiv.className = "tpsEle";
					this.currentDiv.setAttribute("class","tpsEle");
                    this.currentDiv = null;
                    inputSuggText.focus();
					inputSuggText.value = inputSuggText.getAttribute("currValue");
                }
          }
	}
	if(this.evt.keyCode==40){
	    if(this.currentDiv==null){
            this.currentDiv = document.getElementById("container_"+this.inputTxt).firstChild;
			this.currentDiv.className = "tpsEleSeled";
			this.currentDiv.setAttribute("class","tpsEleSeled");
				inputSuggText.value = this.currentDiv.dataObj.name;
				if(this.inputTextChange)this.inputTextChange();
        }else{
    		if(this.currentDiv.nextSibling!=null){
				this.currentDiv.className = "tpsEle";
				this.currentDiv.setAttribute("class","tpsEle");
    			this.currentDiv = this.currentDiv.nextSibling;
				this.currentDiv.className = "tpsEleSeled";
				this.currentDiv.setAttribute("class","tpsEleSeled");
				inputSuggText.value = this.currentDiv.dataObj.name;
				if(this.inputTextChange)this.inputTextChange();
   			}else{
				this.currentDiv.className = "tpsEle";
				this.currentDiv.setAttribute("class","tpsEle");
                this.currentDiv = null;
                inputSuggText.focus();   
				inputSuggText.value = inputSuggText.getAttribute("currValue");
            }
        }
	}
	if(this.evt.keyCode==37){
//		left
	}
	if(this.evt.keyCode==39){
//		right
	}
	if(this.evt.keyCode==13){
//		标志位,不激发keyup
		this.isEnter = true;
		this.setValue(this.evt);
	}
}
inputSuggest.prototype.resetParentNode = function(){
	var parentNode = document.getElementById(this.inputTxt).parentNode;
	if(parentNode.className.indexOf("suggestParent")>-1){
		parentNode.className = parentNode.className.replace("suggestParent","");
		//parentNode.setAttribute("class","suggestParent");
	}	
}
inputSuggest.prototype.keyupCallback = function(boo){
	return function(doc){
		var items = this.getItems();
		if(items.length==0){
			this.hiddenSuggest();
			return false;
	    }
		var parentNode = document.getElementById(this.inputTxt).parentNode;
		if(parentNode.className.indexOf("suggestParent")<0){
			parentNode.className += " suggestParent";
			parentNode.setAttribute("class","suggestParent");
		}
		var container = document.getElementById("container_"+this.inputTxt);
		if(!container){
			container = document.createElement("ul");
			parentNode.appendChild(container);
		}
        container.id = "container_"+this.inputTxt;
		container.className = "suggestUl";
		container.setAttribute("class","suggestUl");
		container.innerHTML = "";
		container.style.display = "inline";
	    inputSuggest.currentDivAry = new Array();
		var obj = this;
		for(var i=0;i<items.length;i++){
		    var oDiv = document.createElement("li");
		    Suggest_Event.obvser(oDiv,"mouseover",this.onMouseOverDiv.call(obj,oDiv),false);
		    Suggest_Event.obvser(oDiv,"click",this.onClickDiv.call(obj,oDiv),false);
			inputSuggest.currentDivAry.push(oDiv);
			container.appendChild(oDiv);
			container.style.height = "auto";//这个地方或许应该根据下拉框的高度和于页面底部的距离做些判断。
			this.fillDiv(oDiv,items[i],i);
			oDiv.className = "tpsEle";
			oDiv.setAttribute("class","tpsEle");
		}
		this.isSuggest = true;
	}
}
inputSuggest.prototype.fillDiv = function(div,item,index){
	div.innerHTML = item["name"];
	div.dataObj = item;
}
inputSuggest.prototype.setValue = function(evt){
    if(!this.currentDiv) return;
	document.getElementById(this.inputTxt).value = this.currentDiv.firstChild.data;
	if(document.getElementById("container_"+this.inputTxt)){
        document.getElementById("container_"+this.inputTxt).style.display = "none";
        this.resetParentNode();
        if (evt.preventDefault) {
              evt.preventDefault();
              evt.stopPropagation();
        } else {
              evt.returnValue = false;
              evt.cancelBubble = true;
        }
    }
	if(this.setValueCallback){this.setValueCallback();}
	this.isSuggest = false;
	this.currentDiv = null;
}
inputSuggest.prototype.onMouseOverDiv = function(ele){
	var obj = this;
    return function(){
        if(obj.currentDiv){
			obj.currentDiv.className = "tpsEle";
			obj.currentDiv.setAttribute("class","tpsEle");
        }
		obj.currentDiv = ele;
		obj.currentDiv.className = "tpsEleSeled";
		obj.currentDiv.setAttribute("class","tpsEleSeled");
    }
}
inputSuggest.prototype.onClickDiv = function(ele){
	var obj = this;
    return function(evnt){
        if(obj.currentDiv){
			obj.currentDiv.className = "tpsEle";
			obj.currentDiv.setAttribute("class","tpsEle");
        }
		var evt;
        if(window.event){
            evt = window.event;
        }else{
            evt = evnt;
        }
		obj.currentDiv = ele;
		obj.currentDiv.className = "tpsEleSeled";
		obj.currentDiv.setAttribute("class","tpsEleSeled");
        obj.setValue.call(obj,evt);
    }
}
inputSuggest.prototype.keyupFun = function(evt,obj){
	var keyupCallback = this.keyupCallbackLoadJs;
	var suggestRequest = this.suggestRequest;
	var objThis = this;
	return function(evnt){
		var keyWord = obj.value;
		suggestRequest.call(objThis,keyWord,keyupCallback);
	}
}
inputSuggest.prototype.keyupFunDelegate = function(obj){
	if(window.event){
        evt = window.event;
    }else{
        evt = this.keyupFunDelegate.caller.arguments[0];
    }
	var thisRef = this;
//	如果键被过滤则不能提交
    if(this.filterKeyCodesFun())return;
	var inputSuggText = document.getElementById(this.inputTxt);
	inputSuggText.setAttribute("currValue",inputSuggText.value);
//	input中的哪些值不会触发提交
	for(var j=0;j<inputSuggest.filterInputValue.length;j++){
		if(document.getElementById(this.inputTxt).value==inputSuggest.filterInputValue[j]){
			if(document.getElementById("container_"+this.inputTxt)){	
				document.getElementById("container_"+this.inputTxt).innerHTML = "";
				this.hiddenSuggest();
				return;
			}
		}		
	}
//	间隔小于250则不触发提交
	if(this.setTimeHandle) clearTimeout(this.setTimeHandle);
	this.setTimeHandle = setTimeout(this.keyupFun.call(thisRef,evt,obj),250);
}