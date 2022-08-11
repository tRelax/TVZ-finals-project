import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilterCategory'
})
export class SearchFilterCategoryPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (!value) return null;
    if (!args) return value;

    args = args.toLowerCase();

    return value.filter(function (data) {
      return (data.name.toLowerCase().includes(args) ||
        data.id.toString().toLowerCase().includes(args))
    });
  }

}
