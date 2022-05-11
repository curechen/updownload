import { request } from './request'

// currentPage 当前所在页面数 pageSize 一个页面返回的文件数量 sortItem 上排序还是下排序
export function getFileList(currentPage, pageSize, sortItem) {
  return request({
    url: '/file/getFileList',
    params: {
      currentPage,
      pageSize,
      sortItem
    }
  })
}

// 文件的上传
export function upload(data, token) {
  return request({
    url: '/file/upload',
    method: 'post',
    data,
    headers: {'token': token}
  })
}

// 文件的删除
export function deleteFile(id) {
  return request({
    url: '/file/delete',
    params: {
      id
    }
  })
}

// 添加文件评论
export function addComment(data) {
  return request({
    url: '/comment/addComment',
    method: 'post',
    data
  })

}

// 获得文件的评论
export function getComment(fid) {
  return request({
    url: '/comment/getComment',
    method: 'get',
    params: {
      fid
    }
  })
}