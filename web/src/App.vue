<template>
    <div id="app">
        <el-container id="container">
            <el-header style="height: auto">
                <h1>Skids  <span style="font-size: .6em; color: #838383;font-weight: normal">刹车-变形金刚人物，有着超强的模仿能力</span></h1>
            </el-header>
            <el-main>
                <router-view></router-view>
            </el-main>
            <el-footer style="text-align: right">
                <span style="font-size: .8em;color: #d3d3d3;">power by skids@1byongche.com</span>
            </el-footer>
        </el-container>
    </div>
</template>

<style>
#container {
    width: 1140px;
    margin: 0 auto;
}
.el-input {
    width: 180px;
    height:40px;
}
</style>