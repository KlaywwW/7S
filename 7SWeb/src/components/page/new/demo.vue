<template>
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
        </el-form>
        <vxe-toolbar perfect>
            <template v-slot:buttons>
                <vxe-button icon="fa fa-plus" status="perfect" @click="insertEvent">新增</vxe-button>
                <vxe-button icon="fa fa-trash-o" status="perfect" @click="removeEvent">移除</vxe-button>
                <vxe-button icon="fa fa-save" status="perfect" @click="saveEvent">保存</vxe-button>
                <vxe-button icon="fa fa-mail-reply" status="perfect" @click="revertEvent">还原</vxe-button>
            </template>
        </vxe-toolbar>
        <vxe-table border resizable show-overflow :data="tableData" :edit-config="{ trigger: 'click', mode: 'cell' }">
            <vxe-table-column type="seq" width="60"></vxe-table-column>
            <vxe-table-column type="expand" width="60">
                <template v-slot:content="{ row, rowIndex }">
                    <div v-for="(item, i) in row.deduct" :key="i">
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

                                    <!-- <img :src="'api/img/' + image.imgName" width="100" height="100" /> -->
                                    <!-- <img src="http://192.168.123.86:8088/img/14-1599953794122.jpeg" width="100" height="100"> -->
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </template>
            </vxe-table-column>
            <vxe-table-column field="item" title="点检项目" :edit-render="{ name: 'input' }"></vxe-table-column>
            <vxe-table-column field="score" title="分数" :edit-render="{ name: 'input' }"></vxe-table-column>
            <vxe-table-column field="checkitems.responsibility" title="责任人" :edit-render="{ name: 'input' }"></vxe-table-column>
            <vxe-table-column title="位置" field="">
                <template v-slot="{ row }">
                    <div v-if="row.depSecend != null">
                        <div>{{ row.depSecend.depSecendName }}</div>
                    </div>
                    <div v-else>
                        <div>{{ row.department.depName }}</div>
                    </div>
                </template>
            </vxe-table-column>
        </vxe-table>
    </div>
</template>

<script>
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
            tableData: []
        };
    },
    methods: {
        insertEvent() {
            const record = {
                sex: '1'
            };
            this.$refs.xTable.insert(record).then(({ row }) => {
                this.$refs.xTable.setActiveCell(row, 'sex');
            });
        },
        removeEvent() {
            const selectRecords = this.$refs.xTable.getCheckboxRecords();
            if (selectRecords.length) {
                this.$XModal.confirm('您确定要删除选中的数据吗?').then((type) => {
                    if (type === 'confirm') {
                        this.$refs.xTable.removeCheckboxRow();
                    }
                });
            } else {
                this.$XModal.message({ message: '请至少选择一条数据', status: 'error' });
            }
        },
        revertEvent() {
            this.$XModal.confirm('您确定要还原数据吗?').then((type) => {
                if (type === 'confirm') {
                    this.$refs.xTable.revertData();
                }
            });
        },
        saveEvent() {
            const { insertRecords, removeRecords, updateRecords } = this.$refs.xTable.getRecordset();
            this.$XModal.alert(
                `insertRecords=${insertRecords.length} removeRecords=${removeRecords.length} updateRecords=${updateRecords.length}`
            );
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
        }
    },
    created() {
        // this.tableData = [];
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

<style>
</style>