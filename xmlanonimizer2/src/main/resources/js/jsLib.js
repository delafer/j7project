$lower = function(str) {
   return str === null ? str : str.toString().toLowerCase();
};

$upper = function(str) {
  return str === null ? str : str.toString().toUpperCase();
};


$random = function(len, charSet) {
    charSet = charSet || 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    len = len || 16;
    var ret = '';
    for (var i = 0; i < len; i++) {
    ret += charSet.charAt(Math.floor(Math.random() * charSet.length));
    }
    return ret;
};

$randomNumber = function(min, max) {
  min = min || 0;
  max = max || 16777215; //24bit
  return Math.floor((Math.random() * max) + min);
};

$substr = function(str, i, j) {
  return str.substr(i, j);
};

$left = function(str, length) {
	  return str.substr(0, length);
};

$right = function(str, length) {
	  return str.slice(-length);
};

$revert = function(str) {
 return str === null ? str : str.toString().split("").reverse().join("");
};

$trim = function(str) {
 return str;
};

replace = function(str) {
 return str;
};

nvl = function(str) {
 return str;
};

nvl2 = function(str) {
 return str;
};





