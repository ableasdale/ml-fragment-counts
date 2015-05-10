<!-- Map<String, Map<String, Counts>> allInMap /-->
<#assign elkeys = allInMap?keys>
<#list elkeys as elkey>
<p>Allin: ${elkey}</p>

    <#assign newkeys = allInMap[elkey]?keys>
    <#assign newmap = allInMap[elkey]>
    <#list newkeys as newkey>
    <p>Key: ${newkey} : ${newmap[newkey].getTotalFragmentsIngestedByForest()}</p>
    </#list>

</#list>



<#assign totes = accruedTotals?keys>
<#list totes as k>
<p>Accc: ${k}</p>

    <#list accruedTotals[k] as item>"${item}",</#list>
</#list>

<hr />

<#assign elkeys = dataSet?keys>
<#list elkeys as elkey>

<p>${elkey}</p>
    <#list dataSet[elkey] as item>
    <!--<p>Date ${item.getDate()}</p>
         p>Orphaned Props ${item.getOrphanedProperties()}</p>
        <p>Frags Ingested by forest ${item.getTotalFragmentsIngestedByForest()}</p>
        <p>Total Frags ${item.getTotalFragmentsIngestedInDatabase()}</p -->
    </#list>


</#list>

// ZINGCHARTS
<script>
var chartData={
"graphset":[
{
"type":"line",
"title":{
"text":"Fragment counts per Forest"
},
"scale-x":{
"values":[<#list allKnownDates as date>"${date}",</#list>]
},
"scale-y" : {

"label":{
"text":"Fragment Counts"
}
},
"series":[

<#assign totes = accruedTotals?keys>
<#list totes as k>
{
"values":[<#list accruedTotals[k] as item>${item},  </#list>],
"text": "${k}"
},
</#list>
]
}
]
};
</script>

<script src="http://cdn.zingchart.com/zingchart.min.js"></script>
<script>


    window.onload=function(){
        zingchart.render({
            id:'chartDiv',
            height:900,
            width:5000,
            data:chartData
        });
    };

</script>


<div class="sixteen columns">
    <div id="chartDiv" style="overflow: scroll; overflow-x: auto;"></div>
</div>
