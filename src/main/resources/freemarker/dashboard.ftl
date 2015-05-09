<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic Fragment Counts <small>Dashboard</small></h2>
        <#include "navigation.ftl">
        <h3>Current file: <small>TODO</small></h3>
    </div>


<#assign totes = accruedTotals?keys>
<#list totes as k>
<p>Accc: ${k}</p>

    <#list accruedTotals[k] as item>"${item}",</#list>
</#list>

    <hr />




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


    <div class="sixteen columns">
        <div id="chartDiv"></div>
    </div>

    <p>${allKnownDates?size} dates</p>
    <#list allKnownDates as date>
        <p><a href="/date/${date}">${date}</a></p>
    </#list>




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

</div>
<#include "footer.ftl">
</body>
</html>

<script>
    var chartData={
        "graphset":[
            {
                "type":"line",
                "background-color":"#003849",
                "utc":true,
                "title":{
                    "y":"7px",
                    "text":"Webpage Analytics",
                    "background-color":"#003849",
                    "font-size":"24px",
                    "font-color":"white",
                    "height":"25px"
                },
                "plotarea":{
                    "margin":"20% 8% 14% 12%",
                    "background-color":"#003849"
                },
                "legend":{
                    "layout":"float",
                    "background-color":"none",
                    "border-width":0,
                    "shadow":0,
                    "width":"75%",
                    "text-align":"middle",
                    "x":"25%",
                    "y":"10%",
                    "item":{
                        "font-color":"#f6f7f8",
                        "font-size":"14px"
                    }
                },
                "scale-x":{
                    "values":[<#list allKnownDates as date>"${date}",</#list>]
                },
                "scale-y":{
                    "values":"0:1000:250",
                    "line-color":"#f6f7f8",
                    "shadow":0,
                    "tick":{
                        "line-color":"#f6f7f8"
                    },
                    "guide":{
                        "line-color":"#f6f7f8",
                        "line-style":"dashed"
                    },
                    "item":{
                        "font-color":"#f6f7f8"
                    },
                    "label":{
                        "text":"Page Views",
                        "font-color":"#f6f7f8"
                    },
                    "minor-ticks":0,
                    "thousands-separator":","
                },
                "crosshair-x":{
                    "line-color":"#f6f7f8",
                    "value-label":{
                        "border-radius":"5px",
                        "border-width":"1px",
                        "border-color":"#f6f7f8",
                        "padding":"5px",
                        "font-weight":"bold"
                    },
                    "scale-label":{
                        "font-color":"#00baf0",
                        "background-color":"#f6f7f8"
                    }
                },
                "tooltip":{
                    "visible":false
                },
                "plot":{
                    "tooltip-text":"%t views: %v<br>%k",
                    "shadow":0,
                    "line-width":"3px",
                    "marker":{
                        "type":"circle",
                        "size":3
                    },
                    "hover-marker":{
                        "type":"circle",
                        "size":4,
                        "border-width":"1px"
                    }
                },
                "series":[
                    {
                        "values":[149.2,174.3,187.7,147.1,129.6,189.6,230,164.5,171.7,163.4,194.5,200.1,193.4,254.4,287.8,246,199.9,218.3,244,312.2,284.5,249.2,305.2,286.1,347.7,278,240.3,212.4,237.1,253.2,186.1,153.6,168.5,140.9,86.9,49.4,24.7,64.8,114.4,137.4],
                        "text":"Pricing",
                        "line-color":"#007790",
                        "legend-marker":{
                            "type":"circle",
                            "size":5,
                            "background-color":"#007790",
                            "border-width":1,
                            "shadow":0,
                            "border-color":"#69dbf1"
                        },
                        "marker":{
                            "background-color":"#007790",
                            "border-width":1,
                            "shadow":0,
                            "border-color":"#69dbf1"
                        }
                    },
                    {
                        "values":[714.6,656.3,660.6,729.8,731.6,682.3,654.6,673.5,700.6,755.2,817.8,809.1,815.2,836.6,897.3,896.9,866.5,835.8,797.9,784.7,802.8,749.3,722.1,688.1,730.4,661.5,609.7,630.2,633,604.2,558.1,581.4,511.5,556.5,542.1,599.7,664.8,725.3,694.2,690.5],
                        "text":"Documentation",
                        "line-color":"#009872",
                        "legend-marker":{
                            "type":"circle",
                            "size":5,
                            "background-color":"#009872",
                            "border-width":1,
                            "shadow":0,
                            "border-color":"#69f2d0"
                        },
                        "marker":{
                            "background-color":"#009872",
                            "border-width":1,
                            "shadow":0,
                            "border-color":"#69f2d0"
                        }
                    },
                    {
                        "values":[536.9,576.4,639.3,669.4,708.7,691.5,681.7,673,701.8,636.4,637.8,640.5,653.1,613.7,583.4,538,506.7,563.1,541.4,489.3,434.7,442.1,482.3,495.4,556.1,505.4,463.8,434.7,377.4,325.4,351.7,343.5,333.2,332,378.9,415.4,385,412.6,445.9,441.5],
                        "text":"Support",
                        "line-color":"#da534d",
                        "legend-marker":{
                            "type":"circle",
                            "size":5,
                            "background-color":"#da534d",
                            "border-width":1,
                            "shadow":0,
                            "border-color":"#faa39f"
                        },
                        "marker":{
                            "background-color":"#da534d",
                            "border-width":1,
                            "shadow":0,
                            "border-color":"#faa39f"
                        }
                    }
                ]
            }
        ]
    };
</script>