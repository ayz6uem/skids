<template>
    <div id="car">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item><a href="/">车辆列表</a></el-breadcrumb-item>
            <el-breadcrumb-item>车辆详情</el-breadcrumb-item>
        </el-breadcrumb>
        <div style="margin-top: 20px;">
            <div style="padding-bottom: 20px;text-align: right">
            </div>
            <el-form ref="car" :model="car" label-width="80px">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <div class="grid-content">
                            <el-form-item label="车牌号">
                                {{car.carNumber}}
                            </el-form-item>
                            <el-form-item label="READY">
                                <i v-if="car.ready" class="el-icon-circle-check" style="color:#67C23A;font-size: x-large"></i>
                                <i v-else class="el-icon-circle-close" style="color:#F56C6C;font-size: x-large"></i>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="grid-content">
                            <el-form-item label="">
                                <el-button-group>
                                    <el-button type="success" @click="onSave" icon="el-icon-check" plain></el-button>
                                    <el-button @click="upStatus" icon="el-icon-upload2" plain></el-button>
                                    <el-button type="danger" @click="unactive" icon="el-icon-delete" plain></el-button>
                                </el-button-group>
                            </el-form-item>

                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <div class="grid-content">
                            <el-form-item label="ODO">
                                <el-input v-model="car.odo"></el-input>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="grid-content">
                            <el-form-item label="时速">
                                <el-input v-model="car.speed"></el-input>
                            </el-form-item>

                        </div>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="12">
                        <div class="grid-content">
                            <el-form-item label="点火">
                                <el-switch v-model="car.acc" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
                            </el-form-item>
                            <el-form-item label="充电">
                                <el-switch v-model="car.charging" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
                            </el-form-item>
                        </div>
                    </el-col>
                    <el-col :span="12">
                        <div class="grid-content">
                            <el-form-item label="车门">
                                <el-switch v-model="car.door" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
                            </el-form-item>
                            <el-form-item label="车锁">
                                <el-switch v-model="car.lock" active-color="#13ce66" inactive-color="#ff4949"></el-switch>
                            </el-form-item>

                        </div>
                    </el-col>
                </el-row>

                <el-form-item label="SOC">
                    <div class="block" style="width:600px;display:inline-block">
                        <el-slider v-model="car.soc" show-tooltip></el-slider>
                    </div>
                </el-form-item>
            </el-form>
            <el-amap vid="map" :zoom="poi.zoom" :center="position" style="height: 550px;border:1px solid #ddd">
                <el-amap-marker :icon="require('~/assets/car.png')" :position="position" draggable="true" :events="poi.events" ></el-amap-marker>
            </el-amap>

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
export default {
    data() {
        let self = this;
        return {
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
                latitude: 0
            },
            poi:{
                zoom: 15,
                events: {
                    dragend(event) {
                        self.car.longitude = event.lnglat.getLng();
                        self.car.latitude = event.lnglat.getLat();
                    }
                }
            }
        }
    },
    computed: {
        position(){
            return [this.car.longitude,this.car.latitude];
        }
    },
    created() {
        this.$http.get("/api/info/"+this.$route.params.id).then(response=>{
            if(response.body.code == 0){
                var info = response.body.data;
                this.car = {
                    id: info.id,
                    carNumber: info.carNumber,
                    online: info.tbox.connected,
                    soc: info.status.soc,
                    odo: info.status.odo,
                    acc: info.status.acc,
                    charging: info.status.charging,
                    ready: info.status.ready,
                    door: info.status.door,
                    lock: info.status.lock,
                    speed: info.status.speed,
                    longitude: info.status.longitude,
                    latitude: info.status.latitude,
                }
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
    methods: {
        onSave() {
            var data = {
                id: this.car.id,
                soc: this.car.soc,
                odo: this.car.odo,
                acc: this.car.acc,
                speed: this.car.speed,
                longitude: this.car.longitude,
                latitude: this.car.latitude,
                ready: this.car.ready,
                charging: this.car.charging,
                door: this.car.door,
                lock: this.car.lock
            }
            this.$http.post("/api/info",data,{emulateJSON:false}).then(response=>{
                if(response.body.code == 0){
                    this.$notify({
                        title: '成功',
                        message: '车辆数据已保存',
                        type: 'success'
                    });
                }
            })
        },
        upStatus() {
            this.$http.get("/api/action/"+this.$route.params.id+"/status/push").then(response=>{
                if(response.body.code == 0){
                    this.$notify({
                        title: '成功',
                        message: '车辆状态推送成功',
                        type: 'success'
                    });
                }
            });

        },
        unactive(){

            this.$http.get("/api/info/"+this.$route.params.id+"/unactive").then(response=>{
                if(response.body.code == 0){
                    this.$alert('车辆已移除, 即将跳转回首页', '提示', {
                        confirmButtonText: '确定',
                        center: true,
                        callback: action => {
                            this.$router.replace("/");
                        }
                    });
                }
            });
        }
    }
}
</script>
