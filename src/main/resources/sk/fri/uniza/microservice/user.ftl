<#-- @ftlvariable name="" type="sk.fri.uniza.microservice.UserView" -->
<html>

    <body background="/images/background.gif" text="white">

        <h1 align="center">User: ${user.userName?html}</h1>
<table style="width:100%">
    <tr>
        <th>Device ID</th> 
        <th>Time Stamp</th> 
        <th>Temperature</th>
     </tr>
        <#list user.userDevices as device>
    <tr>
        <th>${device.deviceId}
        <form method="get" action="/view/user/${user.userName?html}/delete/${device.deviceId?html}">
            <button type="submit">Delete</button>
        </form>     
        </th> 
        <th>${device.data.date} </th>
        <th>
<#assign num = device.data.temperature?number>
<#if (num > 27)>
    <font color="red">${device.data.temperature}</font> 
 </#if>
<#if (num < 13)>
    <font color="blue">${device.data.temperature}</font> 
 </#if>
<#if (num > 13 && num < 27)>
    <font color="Cornsilk">${device.data.temperature}</font> 
 </#if>
</th>
    </tr>

        </#list>
</table>
    </body>
</html>
