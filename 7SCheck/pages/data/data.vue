<template>
	<view>
		<view style="background-color: #ffffff;padding: 1upx 30upx;">
			<view class="">
				<u-table>
					<u-tr class="u-tr">
						<u-th class="u-th" width="7%">No</u-th>
						<u-th class="u-th">項目</u-th>
						<u-th class="u-th" width="28%">部门</u-th>
						<u-th class="u-th" width="11%">扣分</u-th>
						<u-th class="u-th" width="25%">选择</u-th>
					</u-tr>
					<u-tr class="u-tr" v-for="(e,i) in datas" :key="i">
						<u-td class="u-td" width="7%">{{(i+1)}}</u-td>
						<u-td class="u-td text-cut"> 
							<text class="text-cut" style="width: 100%;">{{e.check.item}}</text>
						</u-td>
						<u-td class="u-td" width="28%">{{e.check.depName}}{{e.check.depSecendName}}</u-td>
						<u-td class="u-td" width="11%">
							{{e.formData.minusScore}}
						</u-td>
						<u-td class="u-td" width="25%">
							<view class="col-2">
								<button class="bg-cyan cu-btn sm" @click="save(i)">上传</button>
								<button class="bg-yellow cu-btn sm" @click="del(i)">删除</button>
							</view>
						</u-td>
					</u-tr>
					<!-- <u-tr>
						<u-td>合计分数</u-td>
						<u-td>100</u-td>
					</u-tr> -->
				</u-table>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				datas: [],
				url: 'http://47.112.192.40:8088'
			}
		},
		methods: {
			save(i) {
				console.log(i);
				var that = this;
				console.log(that.datas[i].imgs);
				uni.uploadFile({
					files: that.datas[i].imgs,
					// filePath:that.images[0],
					// name: 'file',
					url: that.url + "/addDeduct",
					formData: that.datas[i].formData,
					success: function(res) {
						console.log(res);
						if (res.statusCode === 200) {
							uni.showToast({
								title: "保存成功",
								icon: "success",
								duration: 1000
							})
							that.datas.splice(i, 1);
							uni.removeStorageSync("check");
							uni.setStorage({
								"key": "check",
								"data": that.datas
							})
						} else {
							uni.showToast({
								title: "发生错误",
								icon: "success",
								duration: 1000
							})
						}

					},
					fail: () => {
						uni.showToast({
							title: "网络连接错误",
							icon: "success",
							duration: 1000
						})
					}

				})

			},
			del(i) {
				var that = this;
				this.datas.splice(i, 1);
				uni.setStorage({
					"key": "check",
					"data": that.datas
				})
			}
		},
		onShow() {
			console.log("onShow");
			var that = this;
			uni.getStorage({
				"key": "check",
				success: res => {
					console.log(res);
					that.datas = res.data;
				},
				fail: res => {
					that.datas = []
				}
			})
		}
	}
</script>

<style>

</style>
