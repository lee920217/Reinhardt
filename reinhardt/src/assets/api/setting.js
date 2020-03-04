const baseUrl = {
  dev: "http://localhost:8360/"
};

const requestAddress = {
  dev: {
    user: `${baseUrl.dev}api/user`,
    task: `${baseUrl.dev}api/task`,
    message: `${baseUrl}/api/message`,
    postcodesio: "https://api.postcodes.io/"
  },
  online: {
    user: `${baseUrl.dev}api/user`,
    task: `${baseUrl.dev}api/task`,
    message: `${baseUrl}/api/message`,
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
