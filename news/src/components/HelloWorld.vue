<template>
  <el-form ref="form" :model="form" label-width="80px">
    <el-form-item label="新闻标题">
      <el-input v-model="form.title"></el-input>
    </el-form-item>
    <el-form-item label="作者">
      <el-input v-model="form.author"></el-input>
    </el-form-item>
    <el-form-item label="地区">
      <el-input v-model="form.location"></el-input>
    </el-form-item>
    <el-form-item label="关键词">
      <el-input v-model="form.keywords"></el-input>
    </el-form-item>
    <el-form-item label="内容">
      <quill-editor
        v-model="form.content"
        ref="myQuillEditor"
        :options="editorOption"
      >
      </quill-editor>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">立即创建</el-button>
      <el-button>取消</el-button>
    </el-form-item>
  </el-form>
</template>
<script>
const toolbarOptions = [
  ['bold', 'italic', 'underline', 'strike'], // toggled buttons
  [{ header: 1 }, { header: 2 }], // custom button values
  [{ list: 'ordered' }, { list: 'bullet' }],
  [{ indent: '-1' }, { indent: '+1' }], // outdent/indent
  [{ direction: 'rtl' }], // text direction
  [{ size: ['small', false, 'large', 'huge'] }], // custom dropdown
  [{ header: [1, 2, 3, 4, 5, 6, false] }],
  [{ color: [] }, { background: [] }], // dropdown with defaults from theme
  [{ font: [] }],
  [{ align: [] }],
  ['link', 'image'],
  ['clean'],
]
import { Post } from '@/assets/js/api.js'
export default {
  data() {
    return {
      form: {
        title: '',
        location: '',
        author: '',
        keywords: '',
        content: '',
      },

      editorOption: {
        placeholder: '',
        theme: 'snow', // or 'bubble'
        modules: {
          toolbar: {
            container: toolbarOptions,
          },
        },
      },
    }
  },
  methods: {
    onSubmit() {
      const self = this
      let postData = {
        query: self.form,
      }
      Post('http://localhost:8360/api/news/pub', postData).then(res => {
        console.log(res)
      })
    },
  },
}
</script>
