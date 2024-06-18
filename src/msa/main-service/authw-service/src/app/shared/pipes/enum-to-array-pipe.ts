import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'enumToArray'
})
export class EnumToArrayPipe implements PipeTransform {
  //https://stackoverflow.com/a/53657893/17495806
  transform(value, args: string[]): any {
    let result = [];
    var keys = Object.keys(value).filter(k => isNaN(Number(k)));
    var values = Object.values(value).filter(k => !isNaN(Number(k)));
    for (var i = 0; i < keys.length; i++) {
      result.push({key: values[i], value: keys[i]});
    }
    return result;
    //or if you want to order the result:
    //return result.sort((a, b) => a.value < b.value ? -1 : 1);
  }
}
