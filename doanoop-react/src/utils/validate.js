const isNumber = (val) => {
  return typeof val == "number";
};

const isValidPrice = (val) => {
  return isNumber(val) && val % 100 === 0;
};

export { isNumber, isValidPrice };
