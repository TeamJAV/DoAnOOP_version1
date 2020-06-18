const convertToLocaleString = (time) => {
  const reg = new RegExp("[0-9]{4}-[0-9]{2}-[0-9]{2}");
  if (reg.test(String(time))) {
    console.log(time)
    console.log(reg.test(String(time)));
    return time;
  }
  const convertedTime = new Date(time).toLocaleString().split(",")[0];
  return convertedTime;
};

export default convertToLocaleString
