<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic Fragment Counts <small>Forest view</small></h2>
    <#include "navigation.ftl">
        <h3>Viewing Forest: <small>${currentForest}</small></h3>
    </div>

    <div class="sixteen columns">
        <div id="chart" style="height:1000px;"></div>
    </div>

    <div class="sixteen columns">
        <div id="chart2" style="height:1000px;"></div>
    </div>


</div>
<#include "footer.ftl">
</body>
</html>


<script>
    $(function() {

        var chart = c3.generate({
            bindto: '#chart',
            size: {
                width: 12000,
                height: 1000
            },
            data: {
                x: 'x',
                //        xFormat: '%Y%m%d', // 'xFormat' can be used as custom format of 'x'
                columns: [
                    ['x', <#list forestData as item>'${item.getDate()}',</#list>],
                    ['Nascent Fragments', <#list forestData as item>${item.getNascentFragments()}, </#list>],
                    ['Orphaned Fragments', <#list forestData as item>${item.getOrphanedProperties()}, </#list>]

                ]
            },
            axis: {
                x: {
                    type: 'timeseries',
                    tick: {
                        format: '%Y-%m-%d'
                    }
                }
            }
        });

        var chart2 = c3.generate({
            bindto: '#chart2',
            size: {
                width: 12000,
                height: 1000
            },
            data: {
                x: 'x',
                //        xFormat: '%Y%m%d', // 'xFormat' can be used as custom format of 'x'
                columns: [
                    ['x', <#list forestData as item>'${item.getDate()}',</#list>],
                    ['Active Fragments', <#list forestData as item>${item.getActiveFragments()}, </#list>],
                    ['Deleted Fragments', <#list forestData as item>${item.getDeletedFragments()}, </#list>],
                    ['Total Fragments / Forest', <#list forestData as item>${item.getTotalFragmentsIngestedByForest()}, </#list>],
                    ['Total Fragments / DB', <#list forestData as item>${item.getTotalFragmentsIngestedInDatabase()}, </#list>]
                ]
            },
            axis: {
                x: {
                    type: 'timeseries',
                    tick: {
                        format: '%Y-%m-%d'
                    }
                }
            }
        });
    });
</script>