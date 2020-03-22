# burpJsEncrypter

More: [重写一个burpJsEncrypter](https://blog.gzsec.org/post/ReCode-A-BurpJsEncrypter/)

Releases:[download](https://github.com/TheKingOfDuck/burpJsEncrypter/releases/download/0.1/burpJsEncrypter-0.1.jar)

### 编译

```
mvn package
```

### 使用

1.将本项目的js目录下的`js`文件以及目标上涉及到加密的所有js文件复制到用户目录下的`/burp/jsFile`目录:

![-w834](https://thekingofduck.github.io/post-images/burpJsEncrypter//15848705260967/15848732791664.jpg)

文件名咋命名完全无所谓,但是main.js中`burpJsEncrypter`函数一定要有。

2.修改加密逻辑

```
//Java调用的主函数
function burpJsEncrypter(rawPayload){

	var encryptedPayload;


	//===============加密开始=================

	encryptedPayload = hex_md5(rawPayload);

	//===============加密结束=================

	return encryptedPayload;
}
```

如上面的代码中的hex_md5函数是加载到内存中的其他js文件中的函数。实战中只需要把页面中所有涉及加密的js文件下载到这个目录即可。

3.设置Payload处理：

![08B84570-D584-4265-9505-27D7F5821E24](https://thekingofduck.github.io/post-images/burpJsEncrypter//15848705260967/08B84570-D584-4265-9505-27D7F5821E24.png)

![-w647](https://thekingofduck.github.io/post-images/burpJsEncrypter//15848705260967/15848737403466.jpg)

选完再开始爆破即可。

![-w973](https://thekingofduck.github.io/post-images/burpJsEncrypter//15848705260967/15848739154768.jpg)

### 注意事项

**明文在哪儿看？**

![-w628](https://thekingofduck.github.io/post-images/burpJsEncrypter//15848705260967/15848741045333.jpg)

**支持再Repeater中加密？**

选中要加密的字段,右键选择`burpJsEncrypter`菜单即可。

![-w519](https://thekingofduck.github.io/post-images/burpJsEncrypter//15848705260967/15848741934886.jpg)

