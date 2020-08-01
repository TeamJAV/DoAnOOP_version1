const convertToLocaleString = (time) => {
  const reg = new RegExp("[0-9]{4}-[0-9]{2}-[0-9]{2}");
  if (reg.test(String(time))) {
    console.log(time);
    console.log(reg.test(String(time)));
    return time;
  }
  const convertedTime = new Date(time).toLocaleString().split(",")[0];
  return convertedTime;
};

const convertToISOString = (date) => {
  let d = new Date(date),
    month = "" + (d.getMonth() + 1),
    day = "" + d.getDate(),
    year = d.getFullYear();

  if (month.length < 2) month = "0" + month;
  if (day.length < 2) day = "0" + day;

  return [year, month, day].join("-");
};

const compareDateString = (s1, s2) => {
  const d1 = new Date(s1);
  const d2 = new Date(s2);
  return d1 <= d2;
}

export default convertToLocaleString;
export { convertToISOString, compareDateString };
