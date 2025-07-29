export function AutoBind(_: any, _2: string, descriptor: PropertyDescriptor){
    const originalMethod = descriptor.value;
    const adjDescriptor = {
        get(){
            return originalMethod.bind(this);
        }
    }

    return adjDescriptor;
}