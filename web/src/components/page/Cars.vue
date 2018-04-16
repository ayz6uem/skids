<template>
    <div id="cars">
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item><a href="/">车辆列表</a></el-breadcrumb-item>
        </el-breadcrumb>
        <div style="margin-top: 20px;">
            <el-input placeholder="车牌号" v-model="carNumber"></el-input>
            <el-button @click="search">激活</el-button>
        </div>
        <div style="margin-top: 20px;">
            <el-table :data="tableData" stripe border style="width: 100%">
                <el-table-column label="车牌号" width="180">
                    <template slot-scope="scope">
                        <router-link :to="'/car/'+scope.row.id"><el-button type="text">{{scope.row.carNumber}}</el-button></router-link>
                    </template>
                </el-table-column>
                <el-table-column label="在线" width="90">
                    <template slot-scope="scope">
                        <i v-if="scope.row.online" class="el-icon-circle-check" style="color:#67C23A;font-size: x-large"></i>
                        <i v-else class="el-icon-circle-close" style="color:#F56C6C;font-size: x-large"></i>
                    </template>
                </el-table-column>
                <el-table-column prop="soc" label="SOC(%)" width="90"></el-table-column>
                <el-table-column prop="odo" label="ODO(km)" width="90"></el-table-column>
                <el-table-column label="点火" width="90">
                    <template slot-scope="scope">
                        <i v-if="scope.row.acc" class="el-icon-circle-check" style="color:#67C23A;font-size: x-large"></i>
                        <i v-else class="el-icon-circle-close" style="color:#F56C6C;font-size: x-large"></i>
                    </template>
                </el-table-column>
                <el-table-column label="充电" width="90">
                    <template slot-scope="scope">
                        <i v-if="scope.row.charging" class="el-icon-circle-check" style="color:#67C23A;font-size: x-large"></i>
                        <i v-else class="el-icon-circle-close" style="color:#F56C6C;font-size: x-large"></i>
                    </template>
                </el-table-column>
                <el-table-column label="READY" width="90">
                    <template slot-scope="scope">
                        <i v-if="scope.row.ready" class="el-icon-circle-check" style="color:#67C23A;font-size: x-large"></i>
                        <i v-else class="el-icon-circle-close" style="color:#F56C6C;font-size: x-large"></i>
                    </template></el-table-column>
                <el-table-column label="车门" width="90">
                    <template slot-scope="scope">
                        <i v-if="scope.row.door" class="el-icon-circle-check" style="color:#67C23A;font-size: x-large"></i>
                        <i v-else class="el-icon-circle-close" style="color:#F56C6C;font-size: x-large"></i>
                    </template></el-table-column>
                <el-table-column label="车锁" width="90">
                    <template slot-scope="scope">
                        <i v-if="scope.row.lock" class="el-icon-circle-check" style="color:#67C23A;font-size: x-large"></i>
                        <i v-else class="el-icon-circle-close" style="color:#F56C6C;font-size: x-large"></i>
                    </template></el-table-column>
                <el-table-column prop="speed" label="时速(km/h)" width="100"></el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <router-link :to="'/car/direction/'+scope.row.id"><el-button type="text" >导航</el-button></router-link>
                    </template></el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                tableData: [
                ],
                carNumber: ''
            }
        },
        created() {
            this.loadData();
        },
        methods : {
            search(){
                if(!this.carNumber){
                    this.$notify.warning({title: '提示',message: '请输入车牌号'});
                    return;
                }
                this.$http.get("/api/info/carNumber",{params:{carNumber:this.carNumber}}).then(response=>{
                    if(response.body.code == 0){
                        var records = response.body.data.records;
                        if(records.length == 0){
                            this.$notify.warning({title:"提示",message:"未找到车辆数据"});
                            return
                        }
                        if(records.length != 1){
                            this.$notify.warning({title:"提示",message:"请输入完整车牌号"});
                            return
                        }
                        var info = records[0];
                        this.active(info.carSn);
                    }else{
                        this.$notify.warning({title:"提示",message:response.body.msg});
                    }
                })
            },
            active(carSn){
                if(carSn){
                    this.$http.get("/api/info/active",{params:{carSn:carSn}}).then(response=>{
                        if(response.body.code == 0){
                            this.$notify.success({title:"成功",message:"激活成功"});
                            this.loadData();
                        }else{
                            this.$notify.warning({title:"提示",message:response.body.msg});
                        }
                    })
                }
            },
            loadData(){
                this.$http.get("/api/info").then(response=>{
                    if(response.body.code == 0){
                        this.tableData = [];
                        response.body.data.forEach(info=>{
                            var obj = {
                                id: info.id,
                                carNumber: info.carNumber,
                                online: info.tbox.connected,
                                soc: info.status.soc.toFixed(1),
                                odo: info.status.odo.toFixed(0),
                                acc: info.status.acc,
                                charging: info.status.charging,
                                ready: info.status.ready,
                                door: info.status.door,
                                lock: info.status.lock,
                                speed: info.status.speed.toFixed(1),
                            }
                            this.tableData.push(obj);
                        });

                    }
                })
            }
        }
    }
</script>
