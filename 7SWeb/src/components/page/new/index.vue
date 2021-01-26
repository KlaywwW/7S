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
                <el-button
                    type="primary"
                    @click="outExcel"
                    v-loading.fullscreen.lock="fullscreenLoading"
                    :loading="exportLoading"
                    :disabled="exportDis"
                    >导出数据</el-button
                >
            </el-form>
        </div>
        <div>
            <el-table :data="tableData" style="width: 100%" max-height="500" v-loading="loading" @expand-change="exChange">
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
                                <el-form-item>
                                    <el-button type="primary" @click="delDeduct(item)">删除</el-button>
                                </el-form-item>
                                <div v-if="item.imagelists.length > 0">
                                    <el-form-item label="点检图片"></el-form-item>
                                    <el-row>
                                        <el-col :sm="6" v-for="(image, j) in item.imagelists" :key="j">
                                            <div class="demo-image__preview">
                                                <el-image
                                                    style="width: 100px; height: 100px"
                                                    :src="'api/img/' + image.imgName"
                                                    :preview-src-list="srcList"
                                                >
                                                </el-image>
                                            </div>
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
                <el-table-column label="责任人" prop="checkitems.responsibility"></el-table-column>
                <el-table-column label="位置">
                    <template slot-scope="props">
                        <div v-if="props.row.depSecend != null">
                            <div>{{ props.row.depSecend.depSecendName }}</div>
                        </div>
                        <div v-else>
                            <div>{{ props.row.department.depName }}</div>
                        </div>
                    </template>
                </el-table-column>
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
            time = time * 1;
            let date = new Date(time);
            return formatDate(date, 'yyyy-MM-dd hh:mm:ss');
        }
    },
    data() {
        return {
            srcList: [],
            fullscreenLoading: false,
            department: [],
            classes: [],
            depId: '',
            classId: '',
            dates: '',
            showClass: false,
            tableData: [],
            loading: false,
            exportDis: false,
            exportLoading: false
        };
    },
    methods: {
        delDeduct(val) {
            let that = this;
            this.$confirm('是否确认完成?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            })
                .then(() => {
                    this.axios.get('api/delDeduct?deductId=' + val.id).then((res) => {
                        this.$message.success(res.data);
                        that.selectItems();
                    });
                })
                .catch(() => {});
            console.log(val);
        },
        // 行展开
        exChange(expandedRows) {
            console.log(expandedRows);
            let that = this;
            this.srcList = [];
            if (expandedRows.deduct != null) {
                let deduct = expandedRows.deduct;
                for (let i = 0; i < deduct.length; i++) {
                    if (deduct[i].imagelists.length > 0) {
                        let img = deduct[i].imagelists;
                        for (let j = 0; j < img.length; j++) {
                            that.srcList.push('api/img/' + img[j].imgName);
                        }
                    }
                }
            }
        },
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
            this.loading = true;

            var that = this;
            var params = {
                startTime: that.dates
            };
            console.log(this.depId + '-' + this.classId + '-' + this.dates);
            this.axios
                .get('api/getDeduct?dates=' + that.dates + '&depId=' + that.depId + '&depSecendId=' + that.classId)
                .then((res) => {
                    console.log(res.data);
                    that.tableData = res.data;
                    that.loading = false;
                })
                .catch((res) => {
                    this.$message('服务器发生错误');
                });
        },
        outExcel() {
            this.exportDis = true;
            this.exportLoading = true;
            let that = this;
            let data = {
                dates: this.dates,
                depId: this.depId,
                depSecendId: this.classId
            };
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
                    that.exportDis = false;
                    that.exportLoading = false;
                    console.log('end');
                }
            };
        }
    },
    created() {
        let that = this;
        this.axios
            .get('api/getDep')
            .then((res) => {
                console.log(res.data);
                that.department = res.data;
            })
            .catch((res) => {
                this.$message('服务器发生错误');
            });
    }
};
</script>


<style scoped>
.fontColor {
    color: #67c23a;
}
</style>

