const baseUrl = {
  dev: "http://localhost:8360/",
  online: "https://4lemna.com/"
};

const requestAddress = {
  dev: {
    user: `${baseUrl.dev}api/user`,
    task: `${baseUrl.dev}api/task`,
    message: `${baseUrl.dev}/api/message`,
    postcodesio: "https://api.postcodes.io/"
  },
  online: {
    user: `${baseUrl.online}api/user`,
    task: `${baseUrl.online}api/task`,
    message: `${baseUrl.online}/api/message`,
    postcodesio: "https://api.postcodes.io/"
  }
};

let exportAddress = {};

if (process.env.NODE_ENV == "development") {
  exportAddress = requestAddress.dev;
} else {
  exportAddress = requestAddress.online;
}

export { exportAddress };
