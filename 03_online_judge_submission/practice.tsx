    function twoSum(arr: number[], target: number): number[] {
        for (let i: number = 0; i < arr.length; i++) {
            for (let j: number = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) return [i, j];
            }
        }


    };

console.log(twoSum([1,2,3,4],7));