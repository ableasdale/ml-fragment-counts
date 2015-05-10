<head>
<style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide:not(.ng-animate){display:none !important;}ng\:form{display:block;}</style>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MarkLogic ErrorLog Analyser - ${title}</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css" />

<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js" charset="utf-8"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.min.css" />
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/c3/0.4.10/c3.min.js"></script>
<script type="text/javascript" src="http://gabelerner.github.io/canvg/rgbcolor.js"></script>
<script type="text/javascript" src="http://gabelerner.github.io/canvg/StackBlur.js"></script>
<script type="text/javascript" src="http://gabelerner.github.io/canvg/canvg.js"></script>



<style type="text/css">
/*-- Chart --*/

.c3 svg {
font: 10px sans-serif;
}
.c3 path, .c3 line {
fill: none;
stroke: #000;
}
.c3 text {
-webkit-user-select: none;
-moz-user-select: none;
user-select: none;
}

.c3-legend-item-tile,
.c3-xgrid-focus,
.c3-ygrid,
.c3-event-rect,
.c3-bars path {
shape-rendering: crispEdges;
}

.c3-chart-arc path {
stroke: #fff;

}
.c3-chart-arc text {
fill: #fff;
font-size: 13px;
}

/*-- Axis --*/

.c3-axis-x .tick {
}
.c3-axis-x-label {
}

.c3-axis-y .tick {
}
.c3-axis-y-label {
}

.c3-axis-y2 .tick {
}
.c3-axis-y2-label {
}

/*-- Grid --*/

.c3-grid line {
stroke: #aaa;
}
.c3-grid text {
fill: #aaa;
}
.c3-xgrid, .c3-ygrid {
stroke-dasharray: 3 3;
}
.c3-xgrid-focus {
}

/*-- Text on Chart --*/

.c3-text {
}

.c3-text.c3-empty {
fill: #808080;
font-size: 2em;
}

/*-- Line --*/

.c3-line {
stroke-width: 1px;
}
/*-- Point --*/

.c3-circle._expanded_ {
stroke-width: 1px;
stroke: white;
}
.c3-selected-circle {
fill: white;
stroke-width: 2px;
}

/*-- Bar --*/

.c3-bar {
stroke-width: 0;
}
.c3-bar._expanded_ {
fill-opacity: 0.75;
}

/*-- Arc --*/

.c3-chart-arcs-title {
font-size: 1.3em;
}

/*-- Focus --*/

.c3-target.c3-focused {
opacity: 1;
}
.c3-target.c3-focused path.c3-line, .c3-target.c3-focused path.c3-step {
stroke-width: 2px;
}
.c3-target.c3-defocused {
opacity: 0.3 !important;
}


/*-- Region --*/

.c3-region {
fill: steelblue;
fill-opacity: .1;
}

/*-- Brush --*/

.c3-brush .extent {
fill-opacity: .1;
}

/*-- Select - Drag --*/

.c3-dragarea {
}

/*-- Legend --*/

.c3-legend-item {
font-size: 12px;
}

.c3-legend-background {
opacity: 0.75;
fill: white;
stroke: lightgray;
stroke-width: 1
}

/*-- Tooltip --*/

.c3-tooltip-container {
z-index: 10;
}
.c3-tooltip {
border-collapse:collapse;
border-spacing:0;
background-color:#fff;
empty-cells:show;
-webkit-box-shadow: 7px 7px 12px -9px rgb(119,119,119);
-moz-box-shadow: 7px 7px 12px -9px rgb(119,119,119);
box-shadow: 7px 7px 12px -9px rgb(119,119,119);
opacity: 0.9;
}
.c3-tooltip tr {
border:1px solid #CCC;
}
.c3-tooltip th {
background-color: #aaa;
font-size:14px;
padding:2px 5px;
text-align:left;
color:#FFF;
}
.c3-tooltip td {
font-size:13px;
padding: 3px 6px;
background-color:#fff;
border-left:1px dotted #999;
}
.c3-tooltip td > span {
display: inline-block;
width: 10px;
height: 10px;
margin-right: 6px;
}
.c3-tooltip td.value{
text-align: right;
}

.c3-area {
stroke-width: 0;
opacity: 0.2;
}

.c3-chart-arcs .c3-chart-arcs-background {
fill: #e0e0e0;
stroke: none;
}
.c3-chart-arcs .c3-chart-arcs-gauge-unit {
fill: #000;
font-size: 16px;
}
.c3-chart-arcs .c3-chart-arcs-gauge-max {
fill: #777;
}
.c3-chart-arcs .c3-chart-arcs-gauge-min {
fill: #777;
}

.c3-chart-arc .c3-gauge-value {
fill: #000;
font-size: 28px;
}






</style>



<#if title = "Dasboard and Overview">
<script src="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.2.0/codemirror.min.js"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.2.0/codemirror.min.css" />
<script>
    // expanders
    $(document).on('click', '.panel-heading span.clickable', function(e){
        var $this = $(this);
        if(!$this.hasClass('panel-collapsed')) {
            $this.parents('.panel').find('.panel-body').slideUp();
            $this.addClass('panel-collapsed');
            $this.find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
        } else {
            $this.parents('.panel').find('.panel-body').slideDown();
            $this.removeClass('panel-collapsed');
            $this.find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
        }
    });

</script>
</#if>

<#if title = "Upload ErrorLog">
<style type="text/css"><#include "../vendor/dropzone.css"></style>
<script><#include "../vendor/dropzone.min.js"></script>
<script>
        $(function () {
            // "myAwesomeDropzone" is the camelized version of the HTML element's ID
            Dropzone.options.dropzone = {
                maxFilesize: 4096 // MB
            };
            $("dropzone").dropzone({ url: "/upload", maxFilesize: 4 });
        });
</script>
</#if>




<!-- TODO - DELETE BELOW -->

<#if title = "Search">
<script><#include "../vendor/chart.min.js"></script>
<script>
    function chart() {
        var ctx = $("#myChart").get(0).getContext("2d");
        var labels = [<#list stacks as stack>"${stack.getOptimisedName()}"<#if stack_has_next>,</#if></#list>];
        var values = [<#list stacks as stack><#assign i = stack.getThreadList()?size>${i}<#if stack_has_next>,</#if></#list>];
        var options = {
            <#if stacks?size &gt; 250>animation: false,</#if>
            scaleShowLabels: false
        }
        var data = {
            labels: labels,
            datasets: [
                {
                    fillColor: "rgba(151,187,205,0.5)",
                    strokeColor: "rgba(151,187,205,1)",
                    pointColor: "rgba(151,187,205,1)",
                    pointStrokeColor: "#fff",
                    data: values,

                    mouseover: function (data) {
                        var active_value = values[data.point.dataPointIndex];
                        var active_date = labels[data.point.dataPointIndex];
                        $("#tooltip").html(active_date+'<br/><span class="bigger">'+active_value+'</span>').css("position", "absolute").css("left", data.point.x-55).css("top", data.point.y-10).css("display", 'block');
                    },
                    // TODO - add a click handler at some stage to take you to the summary for that time - below doesn't seem to work!
                    // mousedown: function (data) {console.log("link..");},
                    mouseout: function (data) {
                        $("#tooltip").html("").css("display", "none");
                    }
                }
            ]
        }
        new Chart(ctx).Line(data, options);
        $("#chart p").hide();
    }
        chart();
    });
</script>
</#if>

<#if title = "Summary View" || title = "Detail View">
<script>
    $(document).keyup(function (e) {
        var currentLocation = parseInt(window.location.href.substring(window.location.href.lastIndexOf('/') + 1, window.location.href.length));
        var uriBase = window.location.href.substring(window.location.href.lastIndexOf('/') + 1, -1);
        if (e.keyCode === 37) {
            if (currentLocation != 0) {
                window.location.replace(uriBase + (currentLocation - 1));
            }
        }
        if (e.keyCode === 39) {
            if (currentLocation < ${total} -1) {
                window.location.replace(uriBase + (currentLocation + 1));
            }
        }
    });
</script>
</#if>

<style type="text/css">

    /* expander css*/

    .clickable{
        cursor: pointer;
    }

    /* end expander css */


    .content a {
        color: #800000;
        text-decoration: none;
    }

    .content a:hover {
        color: red;
        text-decoration: none;
        border-bottom: 1px dotted black;
    }

    .content a:visited {
        text-decoration: none;
        color: #2a5db0;
        border-bottom: 1px dotted black;
    }

    .content h2 a:hover, .content h3 a:hover, .content h4 a:hover {
        text-decoration: none;
    }

    .pure-menu-selected {
        border-bottom: 3px solid orange;
    }

    .term {
        color: #2a5db0;
    }

    .highlight {
        font-weight: bold;
        background: #ffc;
    }

        /* Colouring so we can see problems with a particular thread dump from the dashboard view */
    tr.error {
        color: red;
        border-left: 5px solid red;
    }

        /* Removing the links as the templates for the summaries won't render without data */
    tr.error td a {
        display: none;
    }


    .not-useful {
        border: 1px dashed red;
    }

    .not-useful h4 {
        color: #800000;
        margin-left: 2.5em;
    }

    .not-useful pre {
        background: #ffebeb;
    }

    .useful {
        border: 1px dashed green;
    }

    .useful h4 {
        color: #060;
        margin-left: 2.5em;
    }

    .useful pre {
        background: #e2ffe2;
    }

    #overview td {
        text-align: center;
    }

    .debug, .filename, .sign {
        color: #800000;
    }

    .grn {
        color: #060;
    }

    #exception {
        width: 500px;
        margin: auto;
        padding: 5em 8em;
        border: 30px dotted #800000;
        border-radius: 15em;
    }

    #chart {
        position: relative;
        height: 550px;
        width: 1100px;
        overflow-x:scroll;
    }

    #tooltip {
        opacity:0.7;
        font-weight:bold;
        text-align:center;
        display:none;
        width:8.5em;
        background:black;
        border-radius: 1em;
        color:white;
        padding:0.4em;
    }

    .bigger {font-size:150%;}

    pre.language-cpp {
        font-size:95%;
        overflow-x:scroll;
    }

    code.language-cpp {
        max-width: 3000px;
    }

    #myChart {
        position: absolute;
        top: 50px;
        /* display:none; */
    }

    .search-result {margin-bottom:2em; background:#eee; border:1px dotted #ddd;}
    .search-result h4 {margin-left:2em;}

	footer p {text-align:center;}

</style>
</head>