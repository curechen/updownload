<template>
  <div class="file">
    <div class="upload">
      <input type="file" id="fileInput" multiple ref="upload" />
      <br />
      <el-button @click="uploadClick">上传</el-button>
    </div>
    <div class="info">
      <label for="InputFilename">真实文件名</label>
      <input type="text" name="username" id="InputFilename" v-model="search" />
      <!-- <el-button>查询</el-button> -->

      <el-table
        :data="
          fileList.filter(
            (data) =>
              !search ||
              data.realName.toLowerCase().includes(search.toLowerCase())
          )
        "
        stripe
        style="width: 1100px"
        @sort-change="sortChange"
      >
        <el-table-column prop="id" label="编号" width="50"> </el-table-column>
        <el-table-column prop="storeName" label="存储文件名" width="140">
        </el-table-column>
        <el-table-column prop="realName" label="真实文件名"> </el-table-column>
        <el-table-column prop="uploadTime" label="上传时间"> </el-table-column>
        <el-table-column prop="size" label="文件大小"> </el-table-column>
        <el-table-column prop="author" label="上传人"> </el-table-column>
        <el-table-column prop="spareSpace" label="剩余空间"> </el-table-column>
        <el-table-column
          prop="count"
          label="下载次数"
          sortable="custom"
          width="100"
        >
        </el-table-column>
        <el-table-column prop="state" label="冻结状态"> </el-table-column>
        <el-table-column label="操作" width="275">
          <template slot-scope="scope">
            <el-button
              type="success"
              size="mini"
              round
              @click="download(scope.row.id)"
              >下载</el-button
            >
            <el-button type="primary" size="mini" round @click="freeze($event)"
              >冻结</el-button
            >
            <el-button
              type="danger"
              size="mini"
              round
              @click="deleteClick(scope.row.id)"
              >删除</el-button
            >
            <el-button
              size="mini"
              round
              @click="getComment(scope.row.id, scope.row.uid)"
              >评论</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="block">
        <el-pagination
          layout="prev, pager, next"
          :total="total"
          :page-size="8"
          @current-change="currentClick"
        >
        </el-pagination>
      </div>

      <!-- 评论 -->
      <el-dialog title="评论" :visible.sync="dialogFormVisible">
        <el-form :model="form">
          <el-form-item label="输入评论" :label-width="formLabelWidth">
            <el-input v-model="form.content" autocomplete="off"></el-input>
            <el-button @click="addComment">发表</el-button>
          </el-form-item>
        </el-form>
        <ul class="comment">
          <li v-for="(item, index) in commentData" :key="index">
            <p>
              <span class="user">{{ item.user.name }}:</span>
              {{ item.content }}
            </p>
            <div class="detail">
              <span class="date">{{ item.createTime }}</span>
            </div>
          </li>
        </ul>
      </el-dialog>
    </div>
  </div>
</template>

<script src="../assets/js/jquery-2.1.0.min.js"></script>
<script>
import {
  getFileList,
  upload,
  deleteFile,
  getComment,
  addComment,
} from 'network/file'

export default {
  name: 'File',
  data() {
    return {
      currentPage: 1,
      pageSize: 8,
      // 默认降序排列
      sortItem: 1,
      token: this.$route.params.token,
      // 数据总条数
      total: 0,
      fileList: [],
      search: '',
      form: {
        content: '',
      },
      formLabelWidth: '100px',
      dialogFormVisible: false,
      commentData: [],
      fid: 0,
      uid: 0,
    }
  },
  methods: {
    // 获取列表信息
    getFileList() {
      getFileList(this.currentPage, this.pageSize, this.sortItem).then(
        (res) => {
          // console.log(res)
          this.fileList = res.data.list
          this.total = res.data.totalCount
          // console.log(this.total)
        }
      )
    },
    // 分页点击
    currentClick(val) {
      this.currentPage = val
      this.getFileList()
    },
    // 排序事件
    sortChange(obj) {
      if (obj.order === 'ascending') {
        this.sortItem = 0
        this.getFileList()
      } else {
        this.sortItem = 1
        this.getFileList()
      }
    },
    // 文件上传
    uploadClick() {
      // console.log(this.token);
      var token
      if (this.token === undefined) {
        token = null
      } else {
        token = this.token
      }
      console.log(this.$refs.upload.files[0])
      var data = new FormData()
      data.append('file', this.$refs.upload.files[0])
      data.append('token', token)
      upload(data, token).then((res) => {
        // console.log(res);
        if (res.code === 20000) {
          alert('上传成功')
          this.getFileList()
        } else {
          alert(res.message)
          if (res.message === '用户未登录，请先登录') {
            this.$router.push('/login')
          }
        }
      })
    },
    download(id) {
      console.log(id)
      window.location.href = 'http://localhost:8082/file/download/' + id
    },
    deleteClick(id) {
      deleteFile(id).then((res) => {
        // console.log(res);
        if (res === '') {
          alert('删除成功')
          this.getFileList()
        } else {
          alert('删除失败')
        }
      })
    },
    freeze(event) {
      console.log(event.currentTarget.previousElementSibling.disabled)
      var freezeState = event.currentTarget.previousElementSibling.disabled

      if (!freezeState) {
        // 冻结
        event.currentTarget.children[0].innerHTML = '解冻'
        event.currentTarget.previousElementSibling.disabled = true
        var oldClass = event.currentTarget.previousElementSibling.className
        var newClass = oldClass + ' is-disabled'
        event.currentTarget.previousElementSibling.className = newClass
      } else {
        // 解冻
        event.currentTarget.children[0].innerHTML = '冻结'
        event.currentTarget.previousElementSibling.disabled = false
        var str = event.currentTarget.previousElementSibling.className
        event.currentTarget.previousElementSibling.className = str.replace(
          'is-disabled',
          ''
        )
      }
    },
    // 获取评论
    getComment(fid, uid) {
      this.fid = fid
      this.uid = uid
      this.dialogFormVisible = true
      getComment(fid).then((res) => {
        this.commentData = res
      })
    },
    // 添加评论
    addComment() {
      console.log(this.fid, this.uid)
      var data = new FormData()
      data.append('content', this.form.content)
      data.append('fid', this.fid)
      data.append('uid', this.uid)
      addComment(data).then((res) => {
        console.log(res)
        this.getComment(this.fid, this.uid)
      })
    },
  },
  mounted() {
    this.getFileList()
  },
}
</script>

<style scoped>
.el-input {
  width: 80%;
  margin-right: 10px;
}

.file {
  width: 100vw;
  height: 100vh;
  background-color: white;
}

.info {
  position: absolute;
  top: 0;
  left: 350px;
}

.comment {
  width: 800px;
  height: 300px;
  /* background-color: pink; */
}

.comment li {
  /* width: 500px; */
  /* background-color: burlywood; */
  margin-bottom: 5px;
}

.comment li p {
  font-size: 16px;
  margin-bottom: 12px;
}

.comment li p .user {
  color: #6b91ba;
}

.comment li .detail .date {
  font-size: 12px;
  color: #a4a4a3;
}
</style>
