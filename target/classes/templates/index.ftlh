<!DOCTYPE html>
<html>
<head><title></title>
    <link rel="stylesheet" href="tachyons.min.css">
</head>
<body class="ph3 pt0 pb4 mw7 center sans-serif">
<h1 class="f2 mb0">Web-Calculator</h1>
<p class="f5 mt1 mb4 lh-copy">A web calculator capable of addition, subtraction,
 multiplication, division, power, natural log (ln()) and exp() functions.</p>
<form action="/note" method="POST" enctype="multipart/form-data">
    <ol class="list pl0">
        <li class="mv3"><label class="f6 b db mb2" for="description">Insert your equation here</label>
            <textarea class="f4 db border-box hover-black w-100 measure ba b--black-20 pa2 br2 mb2" rows="5"
                      name="description"><#if description??>${description}</#if></textarea>
            <input class="f6 link dim br1 ba bw1 ph3 pv2 mb2 dib black bg-white pointer" type="submit" value="Add equation"
                   name="add">
        </li>
    </ol>
</form>
<ul class="list pl0"><p class="f6 b db mb2">Results</p>
    <#if strings??>
        <#list strings as string>
            <li class="f5 mt1 mb4 lh-copy"><p class="measure"></p>
                <p>${string}</p>
                <p></p>
            </li>
        <#else>
            <p class="lh-copy f6">You don't have any notes yet.</p>
        </#list>
    </#if>
</ul>
</body>
</html>
