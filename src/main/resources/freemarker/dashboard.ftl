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

    <div class="sixteen columns" id="chart_div" >
        <a class="savePNG" href="#">Download PNG</a>
        <div id="chart"></div>
        <img id='img1'/>
        <canvas id="canvas" width="5000" height="1000"></canvas>
    </div>


    <p>${allKnownDates?size} dates</p>
    <ul>
    <#list allKnownDates as date>
        <li><a href="/date/${date}">${date}</a></li>
    </#list>
    </ul>


</div>
<#include "footer.ftl">
</body>
</html>

<script>

    $(function() {
        $('.savePNG').on('click',function(e){
            e.preventDefault;
            createChartImages();
        });

        var chart = c3.generate({
            bindto: '#chart',
            size: {
                width: 5000,
                height: 1000
            },
            data: {
                x: 'x',
//        xFormat: '%Y%m%d', // 'xFormat' can be used as custom format of 'x'
                columns: [
                    ['x', <#list allKnownDates as date>'${date}',</#list>],

                <#assign totes = accruedTotals?keys>
                <#list totes as k>
                    ['${k}', <#list accruedTotals[k] as item>${item}, </#list>],
                </#list>

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

        var styles;
        var createChartImages = function() {
            // Zoom! Enhance!
            // $('#chart > svg').attr('transform', 'scale(2)');

            // Remove all defs, which botch PNG output
            $('defs').remove();
            // Copy CSS styles to Canvas
            inlineAllStyles();
            // Create PNG image
            var canvas = $('#canvas').empty()[0];
            canvas.width = $('#chart').width() * 2;
            canvas.height = $('#chart').height() * 2;

            var canvasContext = canvas.getContext('2d');
            var svg = $.trim($('#chart > svg')[0].outerHTML);
            canvasContext.drawSvg(svg, 0, 0);
            $(".savePNG").attr("href", canvas.toDataURL("png"))
                    .attr("download", function() {
                        return "image.png";
                    });

        };
        var inlineAllStyles = function() {
            var chartStyle, selector;
            // Get rules from c3_orig.css
            for (var i = 0; i <= document.styleSheets.length - 1; i++) {
                if (document.styleSheets[i].href && document.styleSheets[i].href.indexOf('c3.min.css') !== -1) {
                    if (document.styleSheets[i].rules !== undefined) {
                        chartStyle = document.styleSheets[i].rules;
                    } else {
                        chartStyle = document.styleSheets[i].cssRules;
                    }
                }

            }
            if (chartStyle !== null && chartStyle !== undefined) {
                // SVG doesn't use CSS visibility and opacity is an attribute, not a style property. Change hidden stuff to "display: none"
                var changeToDisplay = function() {
                    if ($(this).css('visibility') === 'hidden' || $(this).css('opacity') === '0') {
                        $(this).css('display', 'none');
                    }
                };
                // Inline apply all the CSS rules as inline
                for (i = 0; i < chartStyle.length; i++) {

                    if (chartStyle[i].type === 1) {
                        selector = chartStyle[i].selectorText;
                        styles = makeStyleObject(chartStyle[i]);
                        $('svg *').each(changeToDisplay);
                        // $(selector).hide();
                        $(selector).not($('.c3-chart path')).css(styles);
                    }
                    $('.c3-chart path')
                            .filter(function() {
                                return $(this).css('fill') === 'none';
                            })
                            .attr('fill', 'none');

                    $('.c3-chart path')
                            .filter(function() {
                                return !$(this).css('fill') === 'none';
                            })
                            .attr('fill', function() {
                                return $(this).css('fill');
                            });
                }
            }
        };
        // Create an object containing all the CSS styles.
        // TODO move into inlineAllStyles
        var makeStyleObject = function(rule) {
            var styleDec = rule.style;
            var output = {};
            var s;
            for (s = 0; s < styleDec.length; s++) {
                output[styleDec[s]] = styleDec[styleDec[s]];
            }
            return output;
        };

    });


</script>