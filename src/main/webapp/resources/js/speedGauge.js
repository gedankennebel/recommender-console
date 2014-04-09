var speedGauge = new JustGage({
    id: "gauge",
    value: 0,
    min: 0,
    max: 1000,
    title: "calculation time (ms)",
    titleFontColor: "white",
    valueFontColor: "white",
    showMinMax: false,
    decimals: true,
    donut: true,
    gaugeWidthScale: 0.2,
    startAnimationType: "linear",
    startAnimationTime: 300,
    refreshAnimationTime: 500,
    refreshAnimationType: "linear",
    counter: true
});
