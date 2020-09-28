<template>
    <div>
        <div>
            <el-form :inline="true" class="demo-form-inline">
                <el-form-item label="部门">
                    <el-select v-model="depId" clearable placeholder="请选择" @change="selectDep">
                        <el-option v-for="item in department" :key="item.id" :label="item.depName" :value="item.id"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="点检位置" v-show="showClass">
                    <el-select v-model="classId" clearable placeholder="请选择">
                        <el-option
                            v-for="item in classes"
                            :key="item.depSecendId"
                            :label="item.depSecendName"
                            :value="item.depSecendId"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="日期">
                    <el-date-picker
                        v-model="dates"
                        type="daterange"
                        range-separator="至"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        value-format="timestamp"
                    ></el-date-picker>
                </el-form-item>
                <el-button type="primary" @click="selectItems">查询</el-button>
                <el-button type="primary" @click="outExcel">导出数据</el-button>
            </el-form>
        </div>
        <div>
            <el-table :data="tableData" style="width: 100%" max-height="1000">
                <el-table-column type="expand">
                    <template slot-scope="props">
                        <el-form label-position="left" inline class="demo-table-expand">
                            <div v-for="(item, i) in props.row.deduct" :key="i">
                                <el-form-item label="扣分原因">
                                    <span class="fontColor">{{ item.reason }}</span>
                                </el-form-item>
                                <el-form-item label="扣除分数">
                                    <span class="fontColor">{{ item.minusScore }}</span>
                                </el-form-item>
                                <el-form-item label="点检人员">
                                    <span class="fontColor">{{ item.name }}</span>
                                </el-form-item>
                                <el-form-item label="点检时间">
                                    <span class="fontColor">{{ item.time | formatDate }}</span>
                                </el-form-item>
                                <div>
                                    <el-form-item label="点检图片"></el-form-item>
                                    <el-row>
                                        <el-col :sm="6" v-for="(image, j) in item.imagelists" :key="j">
                                            <img :src="'api/img/' + image.imgName" width="100" height="100" />
                                            <!-- <img src="http://192.168.123.86:8088/img/14-1599953794122.jpeg" width="100" height="100"> -->
                                        </el-col>
                                    </el-row>
                                </div>
                            </div>
                        </el-form>
                    </template>
                </el-table-column>
                <el-table-column label="序号" type="index"></el-table-column>
                <el-table-column label="点检项目" prop="item"></el-table-column>
                <el-table-column label="分数" prop="score"></el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script>
import { formatDate } from '../../../utils/dateFormat';
export default {
    filters: {
        //方法一: yyyy-MM-dd hh:mm
        formatDate(time) {
            console.log(time);
            time = time * 1;
            let date = new Date(time);
            console.log(new Date(time));
            return formatDate(date, 'yyyy-MM-dd hh:mm:ss');
        }
    },
    data() {
        return {
            department: [],
            classes: [],
            depId: '',
            classId: '',
            dates: '',
            showClass: false,
            tableData: []
        };
    },
    methods: {
        selectDep() {
            console.log(this.depId);
            let that = this;
            this.axios.get('api/getSecend?depId=' + this.depId).then((res) => {
                console.log(res.data);
                if (res.data.length > 0) {
                    that.showClass = true;
                    that.classes = res.data;
                } else {
                    that.showClass = false;
                    that.classId = 0;
                }
            });
        },
        selectItems() {
            if (this.depId == 0 || this.depId == '') {
                 this.$message({
                    message: '请选择部门',
                    type: 'warning'
                });
                return false;
            }
            
            if (this.dates == null || this.dates == '') {
                this.$message({
                    message: '请选择时间段',
                    type: 'warning'
                });
                return false;
            }
            var that = this;
            var params = {
                startTime: that.dates
            };
            console.log(this.depId + '-' + this.classId + '-' + this.dates);
            this.axios.get('api/getDeduct?dates=' + that.dates + '&depId=' + that.depId + '&depSecendId=' + that.classId).then((res) => {
                console.log(res.data);
                that.tableData = res.data;
            });
        },
        outExcel() {
            let data = {
                dates: this.dates,
                depId: this.depId,
                depSecendId: this.classId
            };
            // this.axios
            //     .post('api/exportExcel', data, {
            //         headers: {
            //             responseType: 'blob'
            //         }
            //     })
            //     .then((res) => {
            //         console.log(res.data);
            //         console.log(res);
            //         const link = document.createElement('a');
            //         let blob = new Blob([res.data], { type: 'application/application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
            //         link.style.display = 'none';
            //         link.href = URL.createObjectURL(blob);
            //         console.log('href:' + link.href);
            //         let num = '';
            //         for (let i = 0; i < 10; i++) {
            //             num += Math.ceil(Math.random() * 10);
            //         }
            //         link.setAttribute('download', num + '.xls');
            //         document.body.appendChild(link);
            //         link.click();
            //         document.body.removeChild(link);
            //     });
            let xhr = new XMLHttpRequest();
            xhr.open('post', 'api/exportExcel');
            //如果需要请求头中这是token信息可以在这设置
            xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
            xhr.responseType = 'blob';
            xhr.send(JSON.stringify(data));
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    const blob = new Blob([xhr.response], { type: 'application/application/vnd.ms-excel' });
                    const fileName2 = xhr.getResponseHeader('fileName');

                    // 解决下载文件名称乱码
                    let iconv = require('iconv-lite');
                    iconv.skipDecodeWarning = true; //忽略警告
                    let fileName = iconv.decode(fileName2, 'utf-8');

                    let url = window.URL.createObjectURL(blob);

                    //创建一个a标签元素，设置下载属性，点击下载，最后移除该元素
                    let link = document.createElement('a');
                    link.href = url;
                    link.style.display = 'none';
                    //取出下载文件名
                    const downlaodFileName = decodeURIComponent(fileName);
                    console.log(downlaodFileName);

                    link.setAttribute('download', fileName);
                    link.click();
                    window.URL.revokeObjectURL(url);
                }
            };
        }
    },
    created() {
        let that = this;
        this.axios.get('api/getDep').then((res) => {
            console.log(res.data);
            that.department = res.data;
        });
    }
};
</script>


<style scoped>
.fontColor {
    color: #67c23a;
}
</style>

