<template>
	<view>
		<view class="cu-form-group margin-sm">
			<view class="title">稽查人員</view>
			<input name="input" v-model="checkName"></input>
		</view>
		<view class="cu-form-group margin-sm">
			<view class="title">部門</view>
			<picker mode="selector" :range="department" :value="depIndex" :range-key="'depName'" @change="selectDep" @click="getDep">
				<view class="picker">
					{{depIndex>-1?department[depIndex].depName:'請選擇部門'}}
				</view>
			</picker>
		</view>
		<view class="cu-form-group margin-sm" v-show="showClasses">
			<view class="title">班號</view>
			<picker mode="selector" :range="classes" :value="classIndex" :range-key="'depSecendName'" @change="selectClass">
				<view class="picker">
					{{classIndex>-1?classes[classIndex].depSecendName:'請選擇班號'}}
				</view>
			</picker>
		</view>
		<view style="background-color: #ffffff;padding: 1upx 30upx;">
			<view class="">
				<u-table>
					<u-tr class="u-tr">
						<u-th class="u-th" width="15%">序號</u-th>
						<u-th class="u-th">項目</u-th>
						<u-th class="u-th" width="15%">分數</u-th>
						<u-th class="u-th" width="15%">操作</u-th>
					</u-tr>
					<u-tr class="u-tr" v-for="(e,i) in checkItems" :key="i">
						<u-td class="u-td" width="15%">{{(i+1)}}</u-td>
						<u-td class="u-td text-cut">
							<text class="text-cut" style="width: 100%;">{{e.item}}</text>
						</u-td>
						<u-td class="u-td" width="15%">{{e.score}}</u-td>
						<u-td class="u-td" width="15%">
							<button class="bg-cyan cu-btn sm" @click="deduct(i)">扣分</button>
						</u-td>
					</u-tr>
					<!-- <u-tr>
						<u-td>合计分数</u-td>
						<u-td>100</u-td>
					</u-tr> -->
				</u-table>
			</view>

		</view>
		<view>
			<u-popup v-model="show" mode="center" border-radius="14" :mask-close-able="false" style="z-index: 1;">
				<view class="content">
					<view class="cu-form-group margin-sm">
						<view class="title">點檢項</view>
						<textarea v-model="item" disabled auto-height />
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
							<image :src="images[s]" mode="scaleToFill" @click="selectedImage(s)" >
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
						<button class="cu-btn bg-green margin-lr-xs lg" @click="save">保存</button>
					</view>
					
				</view>
			</u-popup>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				show:false,
				showClasses:false,
				checkName:"",
				depIndex: -1,
				department: [],
				classIndex: -1,
				classes: [],
				checkItems: [],
				touchT:'',
				touchE:'',
				item:'',
				score:'',
				minusScore:'',
				reason:'',
				showImageList:false,
				index:null,
				images:[],
				// url:'http://192.168.123.86:8088',
				url:'http://192.168.123.51:8088',
				// url:'http://47.112.192.40:8088',
				totalScore:0,
				// 页面保存的数据
				datas:[]

			}
		},
		onShow(){
			let that = this;
			uni.request({
				url:that.url+"/getDep",
				method:"GET",
				timeout:2000, 
				success(res) {
					// console.log(res.data);
					that.department=res.data;
				},
					fail: () => {
						uni.showToast({
							title:"网络连接错误",
							icon:"success",
							duration:1000
						})	
					}
			})
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
		},
		methods: {
			getDep(){
				let that = this;
				uni.request({
					url:that.url+"/getDep",
					method:"GET",
					timeout:2000, 
					success(res) {
						// console.log(res.data);
						that.department=res.data;
					},
						fail: () => {
							uni.showToast({
								title:"网络连接错误",
								icon:"success",
								duration:1000
							})	
						}
				})
			},
			selectDep(e) {
				var that=this;
				this.depIndex = e.detail.value;
				this.classIndex=-1;
				console.log(e.detail);
				var data=this.department[this.depIndex].id;
				console.log(data);
				uni.request({
					url:that.url+"/getSecend",
					method:"GET",
					
					data:{
						depId: data
					},
					success(res) {
						console.log(res.data);
						that.classes=res.data;
						if(res.data.length==0){
							console.log(0);
							that.showClasses=false;
							that.classIndex=-2;
							uni.request({
								url:that.url+"/getCheckItems",
								method:"GET",
								data:{
									depId:that.department[that.depIndex].id,
									depSecendId:0
								},
								success: (res) => {
									console.log(res.data);
									that.checkItems=res.data;
								}
							})
						
						}else{
							that.showClasses=true;
						}
					},
					fail: () => {
						uni.showToast({
							title:"网络连接错误",
							icon:"success",
							duration:1000
						})	
					}
				})
			},
			selectClass(e) {
				var that =this;
				if(this.depIndex<0){
					this.classes=[];
				}
				this.classIndex = e.detail.value;
				console.log(this.classes[this.classIndex].depSecendId);
				console.log(this.department[this.depIndex].id);
				uni.request({
					url:that.url+"/getCheckItems",
					method:"GET",
					data:{
						depId:that.department[that.depIndex].id,
						depSecendId:that.classes[that.classIndex].depSecendId
					},
					success: (res) => {
						console.log(res.data);
						that.checkItems=res.data;
					},
					fail: () => {
						uni.showToast({
							title:"网络连接错误",
							icon:"success",
							duration:1000
						})	
					}
				})
				
				
			},
			deduct(e){
				// console.log(this.classes[this.classIndex].depSecendId);
				// console.log(this.department[this.depIndex].id);
				// console.log(e);
				if(this.checkName==null|| this.checkName==""){
					uni.showToast({
						title:"请填写稽查人",
						duration:1000
					})
					return false;
				}
				if(this.classIndex==-1){
					uni.showToast({
						title:"请选择班别",
						duration:1000
					})
					return false;
				}
				this.score=this.checkItems[e].score
				this.item=this.checkItems[e].item
				this.index=e
				this.show=true;
				console.log(this.item);
				
			},
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
				 // console.log(this.touchE - this.touchT);
				// if (this.touchE - this.touchT < 350) {
					uni.previewImage({
						urls: that.images,
						current: that.images[index]
					})
				// }
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
			save(){
				var that =this;
				let checkName=this.checkName;
				let minusScore=this.minusScore;
				let reason=this.reason;
				
				if(checkName == null || checkName==""){
					uni.showToast({
						title:"请填写稽查人名称",
						duration:1000
					})
					return false;
				}else if(minusScore=="" || minusScore==null ){
					uni.showToast({
						title:"请填写扣除分数",
						duration:1000
					})
					return false;
				}else if(minusScore>that.score){
					uni.showToast({
						title:"分数不合理",
						duration:1000
					})
					return false;
				}else if((reason == null || reason=="") && minusScore!=0){
							uni.showToast({
								title:"请填写原因",
								duration:1000
							})
							return false;
						}else{
						let imgs=[]
						let num=0;
						
						for(var i=0; i<this.images.length;i++){
							imgs.push({
								name:'file'+i,
								uri:this.images[i]
							})
						}
						
						if(this.images.length<=0){
							imgs.push({
								name:'',
								uri:''
							})
							num=0;
						}else{
						num=imgs.length;
						}
						console.log(imgs);
						// uni.uploadFile({
						// 	files:imgs,
						// 	// filePath:that.images[0],
						// 	// name: 'file',
						// 	url:that.url+"/addDeduct",
						// 	formData:{
						// 		minusScore:that.minusScore,
						// 		name:that.checkName,
						// 		reason:that.reason,
						// 		itemId:that.checkItems[that.index].id,
						// 		num:num
						// 	},
						// 	success:function(res){
						// 		console.log(res);
						// 		if(res.statusCode===200){
						// 			uni.showToast({
						// 				title:"保存成功",
						// 				icon:"success",
						// 				duration:1000
						// 			})
						// 		}else{
						// 			uni.showToast({
						// 				title:"发生错误",
						// 				icon:"success",
						// 				duration:1000
						// 			})
						// 		}
								
						// 	},
						// 	fail: () => {
						// 		uni.showToast({
						// 			title:"连接错误",
						// 			icon:"success",
						// 			duration:1000
						// 		})	
						// 	}
							
						// })
						console.log(that.classIndex);
						var depSecendName="";
						if(that.classIndex==-2){
							depSecendName=""
						}else{
							depSecendName=that.classes[that.classIndex].depSecendName
						}
						
						that.datas.push({
								imgs:imgs,
								checked:false,
								check:{
									item:that.item,
									depSecendName:depSecendName,
									depName:that.department[that.depIndex].depName
								},
								formData:{
									minusScore:that.minusScore,
									name:that.checkName,
									reason:that.reason,
									itemId:that.checkItems[that.index].id,
									num:num
								}
							});
							uni.setStorage({
								"key": "check",
								"data": that.datas
							})
						console.log(that.datas);
						
						this.checkItems[this.index].score=this.score-this.minusScore
						this.show=false;
						this.item=null;
						this.score=null;
						this.minusScore=null;
						this.reason=null;
						this.images=[]
						}
			}
		}
	}
</script>

<style>
</style>
