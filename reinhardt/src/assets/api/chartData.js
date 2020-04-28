const totalNum = [
  2,
  3,
  7,
  7,
  8,
  12,
  15,
  20,
  23,
  35,
  40,
  51,
  85,
  115,
  163,
  206,
  273,
  319,
  373,
  456,
  590,
  798,
  1140,
  1372,
  1543,
  1951,
  2626,
  3269,
  3983,
  5018,
  5683,
  6650,
  8077,
  9529,
  11658,
  14579,
  17089,
  19522,
  22141,
  25150,
  29474,
  33718,
  38168,
  41903,
  47806,
  51608,
  55242,
  60733,
  65077,
  70272,
  78991,
  84279,
  88621,
  93873,
  98476,
  103093,
  108692,
  114217,
  120067,
  124743,
  129044,
  133495,
  138078,
  143464,
  148377,
  152840,
  157149,
  161145
];
const testNum = [
  195524,
  208837,
  213181,
  232708,
  243421,
  256605,
  269598,
  282374,
  290720,
  302599,
  313769,
  327608,
  341551,
  357023,
  372967,
  386044,
  397670,
  411192,
  425821,
  444222,
  517836,
  543413,
  580437,
  623890
];

const deathNum = [
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  0,
  1,
  2,
  2,
  3,
  5,
  6,
  8,
  8,
  10,
  21,
  35,
  55,
  71,
  103,
  144,
  177,
  233,
  281,
  335,
  422,
  465,
  578,
  759,
  1019,
  1228,
  1415,
  1801,
  2352,
  2921,
  3605,
  4313,
  4932,
  5373,
  6519,
  7097,
  7978,
  8958,
  9875,
  10612,
  11329,
  12107,
  12868,
  13729,
  14576,
  15464,
  16060,
  16509,
  17337,
  18100,
  18738,
  19506,
  20319,
  20732,
  21092,
  21678
];
const hospitalNum = [15239, 15051];

let increaseNum = calIncrease();
function calIncrease() {
  let len = totalNum.length;
  let arr = [];
  for (let i = 0; i < len - 1; i++) {
    arr.push(totalNum[i + 1] - totalNum[i]);
  }
  return arr;
}

const dateList = [
  "01-31",
  "02-06",
  "02-10",
  "02-11",
  "02-12",
  "02-23",
  "02-27",
  "02-28",
  "02-29",
  "03-01",
  "03-02",
  "03-03",
  "03-04",
  "03-05",
  "03-06",
  "03-07",
  "03-08",
  "03-09",
  "03-10",
  "03-11",
  "03-12",
  "03-13",
  "03-14",
  "03-15",
  "03-16",
  "03-17",
  "03-18",
  "03-19",
  "03-20",
  "03-21",
  "03-22",
  "03-23",
  "03-24",
  "03-25",
  "03-26",
  "03-27",
  "03-28",
  "03-29",
  "03-30",
  "03-31",
  "04-01",
  "04-02",
  "04-03",
  "04-04",
  "04-05",
  "04-06",
  "04-07",
  "04-08",
  "04-09",
  "04-10",
  "04-11",
  "04-12",
  "04-13",
  "04-14",
  "04-15",
  "04-16",
  "04-17",
  "04-18",
  "04-19",
  "04-20",
  "04-21",
  "04-22",
  "04-23",
  "04-24",
  "04-25",
  "04-26",
  "04-27",
  "04-28"
];

export { totalNum, deathNum, increaseNum, dateList, testNum, hospitalNum };
