<!DOCTYPE html>
<html lang="en">
<#include "header.ftl">
<body role="document">

<div class="container">

    <div class="sixteen columns">
        <h2>MarkLogic Fragment Counts <small>Host view</small></h2>
    <#include "navigation.ftl">
        <h3>Host: <small>${currentHost}</small></h3>
    </div>

    <#list hostForestList?keys as key>
        <#assign items = hostForestList[key]>
        <h3>${key}</h3>
        <div class="row">
            <div id="chart${key_index}" class="chart"></div>
        </div>
    </#list>


    <div class="row even-spaced">
        <div class="col-md-3"><a href="#" class="btn btn-primary savePNG disabled"><span class="glyphicon glyphicon-download" aria-hidden="true"></span> Download chart as PNG image</a></div>
        <div class="col-md-3"><a href="#" class="btn btn-primary disabled"><span class="glyphicon glyphicon-download" aria-hidden="true"></span> TODO: Download chart as SVG image</a></div>
    </div>

    <hr class="bottom-spaced"/>

</div>
<#include "footer.ftl">
</body>
</html>


<script>
    $(function() {
    <#list hostForestList?keys as key>
        <#assign items = hostForestList[key]>
            var chart${key_index} = c3.generate({
                bindto: '#chart${key_index}',
                size: { width: 1100, height: 500 },
                data: {
                    type: 'spline',
                    x: 'x',
                    columns: [
                        ['x', <#list items as item>'${item.getDate()}',</#list>],
                        ['Nascent Fragments', <#list items as item>${item.getNascentFragments()}, </#list>],
                        ['Orphaned Fragments', <#list items as item>${item.getOrphanedProperties()}, </#list>]
                    ]
                },
                axis: {
                    x: {
                        type: 'timeseries',
                        tick: { format: '%Y-%m-%d'}
                    }
                }
            });
    </#list>
    });
</script>