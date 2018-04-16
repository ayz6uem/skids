<template>
    <div id="car">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item><a href="/">车辆列表</a></el-breadcrumb-item>
            <el-breadcrumb-item>路径规划</el-breadcrumb-item>
        </el-breadcrumb>
        <div style="margin-top: 20px;">
            <div style="padding-bottom: 20px">
                <span>{{car.carNumber}} - 电量：{{car.soc}}% 车速：{{car.speed}}km/h</span>
                <span style="float:right">{{car.instruction}}</span>
            </div>
            <el-amap vid="map" :zoom="map.poi.zoom" cursor="point" :amap-manager="amapManager" :center="position" :plugin="map.plugins" :events="map.events" style="height: 750px;border:1px solid #ddd">
                <el-amap-marker :icon="require('~/assets/car.png')" :offset="map.poi.offset" :position="position" draggable="true" :events="map.poi.events" ></el-amap-marker>
                <el-amap-marker :visible="directionData.visible" :position="directionPosition" ></el-amap-marker>
            </el-amap>
        </div>
        <div style="margin-top: 20px;">
            经度：
            <el-input v-model="directionData.longitude" placeholder="请点击地图选点">
            </el-input>
            纬度：
            <el-input v-model="directionData.latitude" placeholder="">
            </el-input>
            <el-button @click="direction">去这里</el-button>
        </div>

    </div>
</template>

<style>
.amap-icon img{
    position: absolute;
    z-index: -1;
    width:40px;
}
</style>

<script>
import ElButton from "../../../node_modules/element-ui/packages/button/src/button.vue";
import VueAMap from 'vue-amap';
import { AMapManager } from 'vue-amap';

let amapManager = new VueAMap.AMapManager();

export default {
    components: {ElButton},
    data() {
        let self = this;
        return {
            amapManager,
            pathSimplifierIns:undefined,
            car: {
                carNumber: '',
                online: false,
                soc: 0,
                odo: 0,
                acc: true,
                charging: false,
                ready: false,
                door: false,
                lock: true,
                speed: 0,
                longitude: 0,
                latitude: 0,
                route: undefined,
                instruction:''
            },
            map:{
                poi:{
                    zoom: 15,
                    offset: [-20,-20],
                    events: {
                        dragend(event) {
                            self.car.longitude = event.lnglat.getLng();
                            self.car.latitude = event.lnglat.getLat();
                        }
                    }
                },
                plugins: [
                    {
                        pName: 'ToolBar',
                        liteStyle: true,
                    }
                ],
                events: {
                    click(e){
                        self.directionData.longitude = e.lnglat.getLng();
                        self.directionData.latitude = e.lnglat.getLat();
                        self.directionData.visible = true;
                    },
                    init(o){
                    }
                },
            },
            directionData:{
                longitude: 0,
                latitude: 0,
                visible: false,
                draw: false
            }
        }
    },
    computed: {
        position(){
            return [this.car.longitude,this.car.latitude];
        },
        directionPosition(){
            return [this.directionData.longitude,this.directionData.latitude];
        }
    },
    created() {
        this.loadAmapPath();
        this.load();
        this.intervalLoad();
    },
    beforeDestroy(){
        this.removeIntervalLoad();
    },
    methods: {
        loadAmapPath(){
            var self = this;
            AMapUI.load(['ui/misc/PathSimplifier'], function(PathSimplifier) {
                if (!PathSimplifier.supportCanvas) {
                    alert('当前环境不支持 Canvas！');
                    return;
                }
                self.initPathSimplifier(PathSimplifier);
            });
        },
        intervalLoad(){
            this.intId = setInterval(() => {
                this.load();
            }, 30000);
        },
        removeIntervalLoad(){
            if(this.intId){
                clearInterval(this.intId);
            }
        },
        load(){
            this.$http.get("/api/info/"+this.$route.params.id).then(response=>{
                if(response.body.code == 0){
                    var info = response.body.data;
                    this.car.id = info.id;
                    this.car.carNumber = info.carNumber;
                    this.car.soc = info.status.soc.toFixed(1);
                    this.car.speed = info.status.speed.toFixed(1);
                    this.car.longitude = info.status.longitude;
                    this.car.latitude = info.status.latitude;
                    this.car.route = info.route;
                    this.car.instruction = info.instruction;
                    this.drawPath();
                }else{
                    this.$alert('车辆数据不存在, 即将跳转回首页', '提示', {
                        confirmButtonText: '确定',
                        center: true,
                        callback: action => {
                            this.$router.replace("/");
                        }
                    });
                }
            });
        },
        direction(){
            var self = this;
            var longitude = this.directionData.longitude;
            var latitude = this.directionData.latitude;

            this.$http.post("/api/direction/"+this.$route.params.id+"/to",{longitude:longitude,latitude:latitude}).then(response=>{
                if(response.body.code == 0){
                    this.$notify({
                        title: '成功',
                        message: '路径规划成功',
                        type: 'success'
                    });
                    self.load();
                }else{
                    this.$notify({
                        title: '失败',
                        message: response.body.msg,
                        type: 'error'
                    });
                }
            });
        },
        drawPath(){
            if(!this.pathSimplifierIns){
                return;
            }
            if(!this.car.route) {
                this.pathSimplifierIns.setData([]);
                this.directionData.draw = false;
                return;
            }
            if(!this.directionData.draw){
                var path = [];
                this.car.route.steps.forEach(step => {
                    path.push([step.longitude,step.latitude]);
                });
                this.pathSimplifierIns.setData([{
                    name: '路径',
                    path: path
                }]);
                this.directionData.draw = true;
            }

        },
        initPathSimplifier(PathSimplifier){
            this.pathSimplifierIns = new PathSimplifier({
                zIndex: 100,
                map: amapManager.getMap(),
                getPath: function(pathData, pathIndex) {
                    return pathData.path;
                },
                getHoverTitle: function(pathData, pathIndex, pointIndex) {
                    if (pointIndex >= 0) {
                        return pathData.name + '，点:' + pointIndex + '/' + pathData.path.length;
                    }
                    return pathData.name + '，点数量' + pathData.path.length;
                },
                renderOptions: {
                    //轨迹线的样式
                    pathLineStyle: {
                        strokeStyle: 'green',
                        lineWidth: 6,
                        dirArrowStyle: true
                    }
                }
            });
            this.drawPath();
        }
    }
}
</script>
