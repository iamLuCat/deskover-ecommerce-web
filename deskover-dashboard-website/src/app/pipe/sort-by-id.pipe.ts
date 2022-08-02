import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'sortById'
})
export class SortByIdPipe implements PipeTransform {

  transform(value: any, ...args): any {
    return value.sort((a, b) => {
      return a.id - b.id;
    });
  }

}
