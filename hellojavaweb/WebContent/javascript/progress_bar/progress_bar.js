		function ProgressBar(idStr){
		this.idStr = idStr;
	}
	//进度条长度
	ProgressBar.prototype.barLength=200;
	ProgressBar.prototype.type="loop";
	ProgressBar.prototype.times=-1;
	ProgressBar.prototype._loopTimes=0;
	ProgressBar.prototype.period=3000;
	//启动进度条
	ProgressBar.prototype.start=function(){
		document.getElementById(this.idStr+"ProgressBarEm").style.width = this.barLength;
		document.getElementById(this.idStr+"ProgressBarDiv").style.width = this.barLength+16;
		document.getElementById(this.idStr+"ProgressBarDiv").getElementsByTagName("span")[0].style.width = this.barLength;
		if("loop" == this.type){
			var thisRef = this;
			this.looper = setInterval(function(){thisRef._loop();},this.period/20);
		}
	}
	//停止进度条
	ProgressBar.prototype.stop=function(){
		if("loop" == this.type){
			clearInterval(this.looper);
		}
	}
	//私有方法，循环推进进度条
	ProgressBar.prototype._loop=function(){
		var lstLeft = document.getElementById(this.idStr+"ProgressBarEm").style.left;
		lstLeft = parseInt(lstLeft);
		if(lstLeft > this.barLength+2*this.barLength/20){
			this._loopTimes++;
			//alert("this.times="+this.times+" this._loopTimes="+this._loopTimes)
			if(this.times != -1 && this._loopTimes>=this.times){this.stop();this.hidden();return;}
			lstLeft = 0;
		}
		document.getElementById(this.idStr+"ProgressBarEm").style.left = lstLeft+this.barLength/20;
	}
	//隐藏进度条
	ProgressBar.prototype.hidden=function(){
		this.stop();
		var lstLeft = document.getElementById(this.idStr+"ProgressBarDiv").style.display = "none";
	}
	ProgressBar.prototype.setPercent=function(percent){
		document.getElementById(this.idStr+"ProgressBarEm").style.left = this.barLength*percent/100;
	}
