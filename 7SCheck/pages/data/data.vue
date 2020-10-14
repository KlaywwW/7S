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
							<view class="col-3">
								<button class="bg-cyan cu-btn sm" @click="update(i)">修改</button>
								<button class="bg-cyan cu-btn sm" @click="uploadData(i)">上传</button>
								<button class="bg-yellow cu-btn sm" @click="del(i)">删除</button>
							</view>
						</u-td>
					</u-tr>

				</u-table>
			</view>
			<view>

				<u-popup v-model="show" mode="center" border-radius="14" :mask-close-able="false" style="z-index: 1;">
					<view class="content">
						<view class="cu-form-group margin-sm">
							<view class="title">點檢項</view>
							<textarea v-model="item" disabled auto-height />
						</view>
						<view class="cu-form-group margin-sm">
							<view class="title">部门</view>
							<textarea v-model="dep" disabled auto-height />
						</view>
						<view class="cu-form-group margin-sm">
							<view class="title">分數</view>
							<input name="input" v-model="score" disabled></input>
						</view>
						<view class="cu-form-group margin-sm">
							<view class="title">扣分</view>
							<input name="input" type="number" v-model="minusScore"></input>
						</view>
						<view class="cu-form-group margin-sm">
							<view class="title">原因</view>
							 <textarea v-model="reason" auto-height />
						</view>
						<view class="cu-form-group margin-sm" @click="camera">
							<view class="title">拍照</view>
							<uni-text class="cuIcon-cameraadd"></uni-text>
						</view>
						<view class="cu-list grid col-4 justify-center" v-show="showImageList">
							<view class="cu-item justify-center margin-lr-xs" v-for="(k,s) in images" :key="s" style="width: 200rpx;height: 200rpx; position: relative;">
								<image :src="k.uri" mode="scaleToFill" @click="selectedImage(s)" >
								</image>
									<view 
									style="position: absolute;left: 75px;bottom: 70px;width: 20px;height: 21px; color: white;background-color: gray; font-size: 20px;"
									class="cuIcon-close"
									@click="delImg(s)">
									
									</view>
								
								<!-- @touchstart="touchstart(s)" @touchend="touchend" -->
							</view>
						</view>
						<view class="grid col-2 padding-sm cu-list justify-center">
							<button class="cu-btn bg-yellow margin-lr-xs lg" @click="cancel">取消</button>
							<button class="cu-btn bg-green margin-lr-xs lg" @click=" ">保存</button>
						</view>
						
					</view>
				</u-popup>
				
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				show:false,
				url:'',
				datas: [],
				item:'',
				dep:'',
				score:'',
				minusScore:'',
				reason:'',
				showImageList:false,
				index:null,
				images:[],
			}
		},
		methods: {
			camera() {
				this.showImageList = true
				var _this = this;
				uni.chooseImage({
					count: 6, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['camera','album'], //从相册选择 
					success: function(res) {
						// this.images = res.tempFilePaths;
			
						for (var i = 0; i < res.tempFilePaths.length; i++) {
							_this.images.push(res.tempFilePaths[i]);
						}
						console.log(_this.images);
						// console.log(this.images.length);
					}
				})
			
			},
			selectedImage(index) {
				var that = this;
				console.log(that.images);
					uni.previewImage({
						urls: that.images.uri,
						current: that.images[index].uri
					})
			},
			delImg(index){
				console.log(index);
				this.images.splice(index, 1);
			},
			cancel(){
				this.show=false;
				this.item=null;
				this.score=null;
				this.minusScore=null;
				this.reason=null;
				this.images=[]
			},
			save(){},
			update(i){
				console.log(this.datas[i]);
				let upData=this.datas[i]
				this.item=upData.check.item;
				this.dep=upData.check.depName+upData.check.depSecendName;
				this.score=upData.check.score;
				this.minusScore=upData.formData.minusScore;
				this.reason=upData.formData.reason;
				this.images=upData.imgs;
				// this.
				this.show=true;
			},
			uploadData(i) {
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
			this.url=getApp().globalData.url
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
