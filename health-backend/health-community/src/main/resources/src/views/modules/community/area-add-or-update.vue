<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="名字" prop="title">
      <el-input v-model="dataForm.title" placeholder="名字"></el-input>
    </el-form-item>
    <el-form-item label="图片" prop="img">
      <el-input v-model="dataForm.img" placeholder="图片"></el-input>
    </el-form-item>
    <el-form-item label="关注数" prop="fanCount">
      <el-input v-model="dataForm.fanCount" placeholder="关注数"></el-input>
    </el-form-item>
    <el-form-item label="创建者id" prop="creator">
      <el-input v-model="dataForm.creator" placeholder="创建者id"></el-input>
    </el-form-item>
    <el-form-item label="发帖数" prop="questionCount">
      <el-input v-model="dataForm.questionCount" placeholder="发帖数"></el-input>
    </el-form-item>
    <el-form-item label="浏览数" prop="viewCount">
      <el-input v-model="dataForm.viewCount" placeholder="浏览数"></el-input>
    </el-form-item>
    <el-form-item label="点赞数" prop="likeCount">
      <el-input v-model="dataForm.likeCount" placeholder="点赞数"></el-input>
    </el-form-item>
    <el-form-item label="标签" prop="tag">
      <el-input v-model="dataForm.tag" placeholder="标签"></el-input>
    </el-form-item>
    <el-form-item label="是否置顶" prop="sticky">
      <el-input v-model="dataForm.sticky" placeholder="是否置顶"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="更新时间" prop="modifiedTime">
      <el-input v-model="dataForm.modifiedTime" placeholder="更新时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          id: 0,
          title: '',
          img: '',
          fanCount: '',
          creator: '',
          questionCount: '',
          viewCount: '',
          likeCount: '',
          tag: '',
          sticky: '',
          createTime: '',
          modifiedTime: ''
        },
        dataRule: {
          title: [
            { required: true, message: '名字不能为空', trigger: 'blur' }
          ],
          img: [
            { required: true, message: '图片不能为空', trigger: 'blur' }
          ],
          fanCount: [
            { required: true, message: '关注数不能为空', trigger: 'blur' }
          ],
          creator: [
            { required: true, message: '创建者id不能为空', trigger: 'blur' }
          ],
          questionCount: [
            { required: true, message: '发帖数不能为空', trigger: 'blur' }
          ],
          viewCount: [
            { required: true, message: '浏览数不能为空', trigger: 'blur' }
          ],
          likeCount: [
            { required: true, message: '点赞数不能为空', trigger: 'blur' }
          ],
          tag: [
            { required: true, message: '标签不能为空', trigger: 'blur' }
          ],
          sticky: [
            { required: true, message: '是否置顶不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          modifiedTime: [
            { required: true, message: '更新时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/community/area/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.title = data.area.title
                this.dataForm.img = data.area.img
                this.dataForm.fanCount = data.area.fanCount
                this.dataForm.creator = data.area.creator
                this.dataForm.questionCount = data.area.questionCount
                this.dataForm.viewCount = data.area.viewCount
                this.dataForm.likeCount = data.area.likeCount
                this.dataForm.tag = data.area.tag
                this.dataForm.sticky = data.area.sticky
                this.dataForm.createTime = data.area.createTime
                this.dataForm.modifiedTime = data.area.modifiedTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/community/area/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'title': this.dataForm.title,
                'img': this.dataForm.img,
                'fanCount': this.dataForm.fanCount,
                'creator': this.dataForm.creator,
                'questionCount': this.dataForm.questionCount,
                'viewCount': this.dataForm.viewCount,
                'likeCount': this.dataForm.likeCount,
                'tag': this.dataForm.tag,
                'sticky': this.dataForm.sticky,
                'createTime': this.dataForm.createTime,
                'modifiedTime': this.dataForm.modifiedTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
