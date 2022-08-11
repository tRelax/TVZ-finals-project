import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'searchFilter'
})
export class SearchFilterUserPipe implements PipeTransform {

  transform(value: any, args?: any): any {
    if (!value) return null;
    if (!args) return value;

    args = args.toLowerCase();

    return value.filter(function (data) {
      return (data.name.toLowerCase().includes(args) ||
        data.surname.toLowerCase().includes(args) ||
        data.id.toString().toLowerCase().includes(args))
    });
  }

}
