<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="父评论id" prop="parentId">
      <el-input v-model="dataForm.parentId" placeholder="父评论id"></el-input>
    </el-form-item>
    <el-form-item label="评论类型1-一级评论 2-二级评论" prop="type">
      <el-input v-model="dataForm.type" placeholder="评论类型1-一级评论 2-二级评论"></el-input>
    </el-form-item>
    <el-form-item label="评论员" prop="commentator">
      <el-input v-model="dataForm.commentator" placeholder="评论员"></el-input>
    </el-form-item>
    <el-form-item label="创建时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="创建时间"></el-input>
    </el-form-item>
    <el-form-item label="点赞数" prop="likeCount">
      <el-input v-model="dataForm.likeCount" placeholder="点赞数"></el-input>
    </el-form-item>
    <el-form-item label="内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="内容"></el-input>
    </el-form-item>
    <el-form-item label="评论数" prop="commentCount">
      <el-input v-model="dataForm.commentCount" placeholder="评论数"></el-input>
    </el-form-item>
    <el-form-item label="逻辑删除，折叠等等" prop="status">
      <el-input v-model="dataForm.status" placeholder="逻辑删除，折叠等等"></el-input>
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
          parentId: '',
          type: '',
          commentator: '',
          createTime: '',
          likeCount: '',
          content: '',
          commentCount: '',
          status: ''
        },
        dataRule: {
          parentId: [
            { required: true, message: '父评论id不能为空', trigger: 'blur' }
          ],
          type: [
            { required: true, message: '评论类型1-一级评论 2-二级评论不能为空', trigger: 'blur' }
          ],
          commentator: [
            { required: true, message: '评论员不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '创建时间不能为空', trigger: 'blur' }
          ],
          likeCount: [
            { required: true, message: '点赞数不能为空', trigger: 'blur' }
          ],
          content: [
            { required: true, message: '内容不能为空', trigger: 'blur' }
          ],
          commentCount: [
            { required: true, message: '评论数不能为空', trigger: 'blur' }
          ],
          status: [
            { required: true, message: '逻辑删除，折叠等等不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/community/comment/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.parentId = data.comment.parentId
                this.dataForm.type = data.comment.type
                this.dataForm.commentator = data.comment.commentator
                this.dataForm.createTime = data.comment.createTime
                this.dataForm.likeCount = data.comment.likeCount
                this.dataForm.content = data.comment.content
                this.dataForm.commentCount = data.comment.commentCount
                this.dataForm.status = data.comment.status
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
              url: this.$http.adornUrl(`/community/comment/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'parentId': this.dataForm.parentId,
                'type': this.dataForm.type,
                'commentator': this.dataForm.commentator,
                'createTime': this.dataForm.createTime,
                'likeCount': this.dataForm.likeCount,
                'content': this.dataForm.content,
                'commentCount': this.dataForm.commentCount,
                'status': this.dataForm.status
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
